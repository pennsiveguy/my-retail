#!/bin/bash

mongoimport --db my-retail --collection item-price --drop --file ./mongo.my-retail.prices.json

