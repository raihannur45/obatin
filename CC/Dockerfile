FROM node:lts-alpine

WORKDIR /home/node/

COPY . .
COPY package*.json ./

RUN apk update
RUN apk upgrade
RUN apk add bash

RUN npm install

USER node
EXPOSE $PORT

CMD ["/bin/bash", "entrypoint.sh"]