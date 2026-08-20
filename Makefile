PYTHON ?= $(shell python3 -c "import sys; print(sys.executable)" 2>/dev/null || python -c "import sys; print(sys.executable)")

.PHONY: test test-python test-java

test:
	$(PYTHON) scripts/test_runner.py --all

test-python:
	$(PYTHON) scripts/test_runner.py --python

test-java:
	$(PYTHON) scripts/test_runner.py --java

