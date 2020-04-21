#!/bin/bash

mongoimport --db my-retail --collection item-price --drop --file ./src/test/resources/mongo.my-retail.prices.json

