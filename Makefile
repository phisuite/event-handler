NAME=event-handler

build:
	docker build -t phisuite/${NAME} .

publish:
ifdef VERSION
	docker tag phisuite/${NAME} phisuite/${NAME}:${VERSION} && \
	docker push phisuite/${NAME}:latest && \
	docker push phisuite/${NAME}:${VERSION}
else
	echo "VERSION not defined"
endif

debug:
	sbt ~run

