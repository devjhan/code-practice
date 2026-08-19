PYTHON ?= .venv/bin/python

.PHONY: test test-python test-java

test:
	@status=0; \
	$(MAKE) --no-print-directory test-python || status=1; \
	$(MAKE) --no-print-directory test-java || status=1; \
	exit $$status

test-python:
	$(PYTHON) -m pytest

test-java:
	./gradlew test
