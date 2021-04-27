FROM openjdk:8-jdk-alpine
MAINTAINER jin820100449@qq.com
RUN mkdir /alidata && \
    mkdir /alidata/app && \
    mkdir /alidata/app/site && \
    mkdir /alidata/app/site/log && \
    mkdir /alidata/app/site/config
VOLUME /alidata/app/site/log
WORKDIR /alidata/app/site
ENV JAVA_OPTS=""
ENV SW_OPTS=""
ENV APP_PROFILE="prod"
ENTRYPOINT [ "sh", "-c", "java $SW_OPTS $JAVA_OPTS -jar /alidata/app/site/zj3measure-service.jar --spring.profiles.active=$APP_PROFILE -Dfile.encoding=utf-8"]
COPY zj3measure-service.jar /alidata/app/site/zj3measure-service.jar
#COPY application-prod.properties /alidata/app/site/config
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


