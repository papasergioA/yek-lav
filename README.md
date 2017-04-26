[![Build Status](https://travis-ci.org/papasergioA/yek-lav.svg?branch=master)](https://travis-ci.org/papasergioA/yek-lav)

# yek-lav

## Présentation
L'application *yek-lav* propose un service réseau de stockage clé-valeur.

## Fonctionnalités
### Structures et Types
Il est possible de stocker ces types de valeurs :
 - Integer
 - String

Les structures de données disponibles sont :
 - table de hachage
 - liste (à implémenter)

### Politique
La politique *LRU* est prise en charge lorsque le seuil maximum de clé stockée est atteint. Cette fonctionnalité est possible via l'utilisation en interne d'une *LinkedHashMap*.
