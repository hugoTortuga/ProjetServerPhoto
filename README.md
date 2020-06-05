# Projet Serveur Photo
***
L'application ServeurPhoto est un appplication Java dédiée à la mise en ligne, à la sauvegarde et à la visualisation de photos par des utilisateur·rice·s.

Ce projet a été réalisé en équipe, composée de : Maëlle Laporte, Hugo Malglaive et Raphaël Robert, dans le cadre de notre formation d'ingénieur·e informatique au CNAM de Reims, du 27 avril au 05 mai 2020 (prolongé au 05 juin).

# Pré-requis
***
Cette application utilise le moteur de production Apache Maven, qui gère les dépendances avec les différentes librairies utilisées. 
Il n'y aucun pré-requis.

# Langages et outils utilisés
***

| Utilisation                    | Langages/outils       |
|--------------------------------|:---------------------:|
| Développement                  | Java12                |
| Interface graphique            | JavaFX11              |
| Conception interface graphique | Scene Builder         |
| Base de données                | JDBC, MariaDB         |
| Versionnage                    | GitHub                |


# Conception de l'application
***
Nous avons conçu cette application en réalisant une analyse des besoins

## Use case
![](image.png)

## Analyse Merise MCD
![](image.png)

## Diagramme de classe UML
![](image.png)

# Charte graphique
* ![#deb0dd](https://placehold.it/15/deb0dd/000000?text=+) 
* ![#c6d6b2](https://placehold.it/15/c6d6b2/000000?text=+)
* ![#a6a695](https://placehold.it/15/a6a695/000000?text=+)
* ![#593951](https://placehold.it/15/593951/000000?text=+)
* ![#5e6b3b](https://placehold.it/15/5e6b3b/000000?text=+)

# Fonctionnement de l'application
***
Il faut dans un premier temps lancecr le projet serveur, pour autoriser ensuite une connexion client lors du lancement de        l'application

## Connexion/Inscription
L'utilisateur·rice peut ensuite se connecter, ou s'inscrire. L'administrateur·rice peut également posséder un compte utilisateur.
Il faut pour cela rentrer un nom, un prénom, une date de naissance, un pseudonyme et un mot de passe, qui sera crypté. 
  
## Page d'accueil
La page d'accueil liste toutes les photos que les utilisateur·rice·s auront mises en ligne en idenquant une visibilité publique. Cette page contient également un bouton vers le profil de l'utilisateur·rice, et un autre afin de mettre en ligne une photo.

Une barre de recherche permet à l'heure actuelle de rechercher par le nom d'une photo publique.

## Profil
La vue du profil permet à l'utilisateur·rice de visualiser ses propres photos, d'en mettre une en ligne ou bien de modifier ses informations personnelles. Cette dernière fonctionnalité n'est pas encore disponible.

## Mettre une photo en ligne
Lors de la mise en ligne d'une photo, celle-ci doit être importée en choisisant un fichier via le bouton "Choisir une photo". Il est également possible de changer le nom de la photo, d'y ajouter un lieu, une description ainsi que des tags qui permettront de référencer la photo.

# Suite éventuelle
***
* Gérer un système de relation "Amis" entre utilisateur·rice·s
* Recherche par tags
