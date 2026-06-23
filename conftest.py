import inspect
from collections import deque

import pytest


# ---------------------------------------------------------------------
# 1. 동적 타입 변환 및 패치 헬퍼 함수
# ---------------------------------------------------------------------
def _get_leetcode_classes():
    """현재 로드된 모듈에서 ListNode와 TreeNode 클래스를 찾습니다."""
    import sys

    ListNode, TreeNode = None, None
    for module in list(sys.modules.values()):
        if not ListNode and hasattr(module, "ListNode"):
            ListNode = module.ListNode
        if not TreeNode and hasattr(module, "TreeNode"):
            TreeNode = module.TreeNode
    return ListNode, TreeNode


def to_linked_list(arr, ListNodeClass):
    """파이썬 list를 ListNode 객체로 변환"""
    if not arr or not ListNodeClass:
        return None
    dummy = ListNodeClass(0)
    curr = dummy
    for val in arr:
        curr.next = ListNodeClass(val)
        curr = curr.next
    return dummy.next


def listnode_to_list(head):
    """ListNode를 파이썬 list로 변환"""
    res = []
    curr = head
    while curr:
        res.append(curr.val)
        curr = curr.next
    return res


# ---------------------------------------------------------------------
# 2. Pytest 내부 훅 (Hook) 설정
# ---------------------------------------------------------------------


@pytest.hookimpl(tryfirst=True)
def pytest_pyfunc_call(pyfuncitem):
    """
    테스트 함수가 호출되기 직전 파라미터를 가로챕니다.
    입력값이 파이썬 list인데, 해당 알고리즘 매개변수 타입 힌트가
    ListNode 관련이면 자동으로 변환하여 주입합니다.
    """
    ListNodeClass, _ = _get_leetcode_classes()
    if not ListNodeClass:
        return None  # 클래스를 못 찾으면 기본 동작 유지

    # 테스트 함수에 전달될 인자들 (현재는 list 상태)
    funcargs = pyfuncitem.funcargs
    # 실제 실행할 알고리즘 함수(예: addTwoNumbers)의 시그니처 분석
    target_func = pyfuncitem.obj

    # 💡 만약 파라미터 중 파이썬 list가 있다면 자동으로 ListNode로 빌드
    for arg_name, arg_val in list(funcargs.items()):
        if isinstance(arg_val, list):
            # 변수명이나 정황상 ListNode 주입이 필요한 경우 래핑
            # (안전하게 모든 list 입력을 변환하거나, 특정 인자 조건에 맞춤)
            funcargs[arg_name] = to_linked_list(arg_val, ListNodeClass)

    # 객체 간 비교(==)를 위한 패치도 함께 적용
    def listnode_eq(self, other):
        if isinstance(other, list):
            return listnode_to_list(self) == other
        if self.__class__.__name__ == getattr(other, "__class__", {}).__name__:
            return listnode_to_list(self) == listnode_to_list(other)
        return False

    ListNodeClass.__eq__ = listnode_eq


@pytest.hookimpl(tryfirst=True)
def pytest_runtest_setup(item):
    """단언문(assert) 비교 오버라이딩을 위한 사전 주입"""
    ListNodeClass, _ = _get_leetcode_classes()
    if ListNodeClass:

        def listnode_eq(self, other):
            if isinstance(other, list):
                return listnode_to_list(self) == other
            if self.__class__.__name__ == getattr(other, "__class__", {}).__name__:
                return listnode_to_list(self) == listnode_to_list(other)
            return False

        ListNodeClass.__eq__ = listnode_eq
