# Fee Rules Engine [![Build Status](https://travis-ci.org/Dayde/fee-rules-engine.svg?branch=master)](https://travis-ci.org/Dayde/fee-rules-engine)

This webservice aims at managing a set of rules
desgined to select the appropriate fee for a specific mission
based on different parameters.

## Continuous Delivery on [Heroku](https://fee-rules-engine.herokuapp.com/rules)

## Getting Started

Launching the project should be as simple as :
```shell
mvn spring-boot:run -DIPSTACK_API_KEY=<your_ipstack_api_key>
```

The first run may be a bit long due to the embedded MongoDB download.

### HTTP endpoints

#### `/rules` RESTful endpoint

`GET`/`PUT`/`POST`/`DELETE` on `/rules` or `/rules/{id}` should have conventional REST behaviours
except for collection operation for which only `GET` is supported.

Rule sample :
```json
{
  "name": "spain or repeat",
  "fee": 8,
  "restriction": {
    "@type": "boolean",
    "operator": "AND",
    "restrictions": [
      {
        "@type": "boolean",
        "operator": "OR",
        "restrictions": [
          {
            "@type": "comparison",
            "operator": "GT",
            "attributeName": "missionLength",
            "value": 5184000000
          },
          {
            "@type": "comparison",
            "operator": "GT",
            "attributeName": "commercialRelation.duration",
            "value": 5184000000
          }
        ]
      },
      {
        "@type": "comparison",
        "operator": "EQ",
        "attributeName": "client.countryCode",
        "value": "ES"
      },
      {
        "@type": "comparison",
        "operator": "EQ",
        "attributeName": "freelancer.countryCode",
        "value": "ES"
      }
    ]
  }
}
```

#### `/fee` Http endpoint 

`POST` on `/fee` with mission informations should return the appropriate fee to be applied
and the name of the corresponding rule if it is not the default fee.

```json
{
  "client" : {
    "ip" : "217.127.206.227"
  },
  "freelancer" : {
    "ip" : "217.127.206.227"
  },
  "missionLength" : 1728000000,
  "commercialRelation" : {
    "firstMission" : 1551977795671,
    "lastMission" : 1562342195691
  }
}
```
