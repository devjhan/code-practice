package leetcode.p0307_range_sum_query_mutable.segement_tree;

class Node {
    int start;
    int endExclusive;
    int sum;
    Node(int start, int endExclusive, int sum) {
        this.start = start;
        this.endExclusive = endExclusive;
        this.sum = sum;
    }
}
class NumArray {
    private Node[] tree;
    private final int[] nums;
    private int getLeft(int index) { return index * 2 + 1; }
    private int getRight(int index) { return index * 2 + 2; }


    private int init(int[] nums, int start, int endExclusive, int currIndex) {
        if (endExclusive - start == 1) {
            tree[currIndex] = new Node(start, endExclusive, nums[start]);
            return nums[start];
        }
        int mid = start + (endExclusive - start) / 2;
        int leftSum = init(nums, start, mid, getLeft(currIndex));
        int rightSum = init(nums, mid, endExclusive, getRight(currIndex));
        tree[currIndex] = new Node(start, endExclusive, leftSum + rightSum);
        return leftSum + rightSum;
    }

    public NumArray(int[] nums) {
        this.nums = nums;
        if (nums.length == 0) return;

        tree = new Node[4 * nums.length];
        init(nums, 0, nums.length, 0);
    }

    public void update(int index, int val) {
        int diff = val - nums[index];
        nums[index] = val;

        int curr = 0;

        while (true) {
            if (tree[curr].endExclusive - tree[curr].start == 1) break;
            if (tree[curr].start <= index && index < tree[curr].endExclusive) tree[curr].sum += diff;

            if (index < tree[curr].start + (tree[curr].endExclusive - tree[curr].start) / 2) curr = getLeft(curr);
            else curr = getRight(curr);
        }
        tree[curr].sum += diff;
    }

    public int sumRange(int left, int right) {
        return sumRange(0, left, right + 1);
    }

    private int sumRange(int curr, int left, int right) {
        if (right <= tree[curr].start || left >= tree[curr].endExclusive) return 0;
        else if (left <= tree[curr].start && right >= tree[curr].endExclusive) return tree[curr].sum;
        else {
            int leftSum = sumRange(getLeft(curr), left, right);
            int rightSum = sumRange(getRight(curr), left, right);
            return leftSum + rightSum;
        }
    }
}
class SegementTreeTest {
}
