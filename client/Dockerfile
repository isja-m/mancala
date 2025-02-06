# syntax=docker/dockerfile:1

ARG NODE_VERSION=22.12.0

FROM node:${NODE_VERSION}-alpine

# Use production node environment by default.
ENV NODE_ENV development

WORKDIR /usr/src/client

COPY . .

# Install necesary packages
RUN npm install

RUN npm i -g vite

# Expose the port that the application listens on.
EXPOSE 5173

# Run the application.
CMD npm run dev
