#!/bin/bash
if [ -v MIGRATE ]; then
    npm run migrate
fi

exec npm run start