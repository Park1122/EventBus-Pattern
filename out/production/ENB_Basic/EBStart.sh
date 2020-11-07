#!/bin/bash

rmiregistry &
sleep 1
java Framework.EventManager &
