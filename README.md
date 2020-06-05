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
| IDE                            | IntelliJ IDEA         |


# Conception de l'application
***
Nous avons conçu cette application en réalisant une analyse des besoins

## Use case
![](http://tidusdotexe.fr/projet%20flauzac/use%20case.PNG)

## Analyse Merise MCD
![](http://tidusdotexe.fr/projet%20flauzac/mcd.PNG)

## Diagramme de classe UML
![](http://tidusdotexe.fr/projet%20flauzac/diag.PNG)

# Charte graphique
* ![#deb0dd](https://placehold.it/15/deb0dd/000000?text=+)
* ![#c6d6b2](https://placehold.it/15/c6d6b2/000000?text=+)
* ![#a6a695](https://placehold.it/15/a6a695/000000?text=+)
* ![#593951](https://placehold.it/15/593951/000000?text=+)
* ![#5e6b3b](https://placehold.it/15/5e6b3b/000000?text=+)

# Présentation de l'application 
***
(Vidéo)
[![Vidéo de présention](https://img.youtube.com/vi/43Cntxb5EN8/maxresdefault.jpg)](https://www.youtube.com/watch?v=43Cntxb5EN8&feature=youtu.be)

# Fonctionnement de l'application
***
Il faut dans un premier temps lancer le projet serveur, pour autoriser ensuite une connexion client lors du lancement de        l'application

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

# Côté technique 
***
## Base de données
(Vidéo)
[![Vidéo BDD](https://img.youtube.com/vi/Lc_GPG0_r-0/maxresdefault.jpg)](https://www.youtube.com/watch?v=Lc_GPG0_r-0&feature=youtu.be)

## API
Elle est composée de méthodes permettant l'envoi et la réception de données au serveur. Certaines actions basiques possèdent leur propre méthode comme la création d'un utilisateur dans la base, ou encore l'upload de photos. Une méthode générique appelée "getQuery" permet de créer une requete avec divers paramètres possibles. Cette méthode retourne un Hashtable avec le libellé de la colonne en clé et sa valeur associée.

Dans le futur, nous pourrions faire évoluer cette API en web (si l'interdiction se levait) pour plus de facilité, performance et utilité; avec l'utilisation du JSON. 

## Hashage
L'utilisation de mots de passe nous impose de hacher ces derniers. Le choix s'est porté sur PBKDF2WithHmacSHA1, étant donné que beaucoup d'algorithme de hashage ne sont plus fiables (MD5, SHA1, ...). On utilise également un sel avec cet algorithme de hachage. La classe est peu complexe, ne comptant qu'une méthode static permettant de retourner un tableau de byte depuis une string donnée en paramètre.

# Suite éventuelle
***
* Gérer un système de relation "Amis" entre utilisateur·rice·s
* Recherche par tags
