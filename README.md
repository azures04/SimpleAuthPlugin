# JFP-Auth
 Plugin Minecraft qui permet aux joueurs de se connecter au serveur Minecraft de JulesFerry-Plus en utilisant leur code donner sur la messagerie de l'ENT (monlycée.net)

# Comment ça se passe ❓
## Les Commandes 📟
### Register
#### Usage ⚙️
`/register <code fourni sur l'ENT>`
#### Fonctionnement 📜
Lorsque le joueur utilise cette commande, dans un premier temps, on vérifié si il est dans la liste des personnes non connecté :
```java
public static ArrayList<String> PLAYERS = new ArrayList();
```
Cette liste se trouve dans la classe `PlayerListener`
Si non, alors dans ce cas on renvoi jutse un message pour dire que le joueur est actuellement déjà connecté.
Si oui, alors on prend le code mis en argument dans la commande *(en cas d'absence de celui-ci, un message d'erreur sera envoyé)*,
Si le code existe on attribut le pseudo du joueur au code de celui-ci et on réactive la possibilités pour le joueur de se mouvoir, d'être vu et de prendre des dégâts et on enregistre ceci dans le fichier `players.json`.
Si le code est inexistant on expulse le joueur du serveur avec un message l'informant que ce code est innexistant.
#### Permission 🔒
`julesferryplus.auth.register`
### Login
#### Usage ⚙️
`/login <code fourni sur l'ENT>`
#### Fonctionnement 📜
Lorsque le joueur utilise cette commande, dans un premier temps, on vérifié si il est dans la liste des personnes non connecté, *(de la même manière que dans la partie Register)*.
Si non, alors dans ce cas on renvoi jutse un message pour dire que le joueur est actuellement déjà connecté.
Si oui, alors on prend le code mis en argument dans la commande *(en cas d'absence de celui-ci, un message d'erreur sera envoyé)*,
Si le code existe on vérifie si le pseudo attribué correspond,
Si oui on réactive la possibilités pour le joueur de se mouvoir, d'être vu et de prendre des dégâts.
Si non le joueur est expulsé et est informé que son code est invalide.
Si le code est inexistant on expulse le joueur du serveur avec un message l'informant que ce code est innexistant.
#### Permission 🔒
`julesferryplus.auth.login`
## Classes ⌨
### PlayerListener`
Cette classe permet de géré la liste des joueurs non connectés, lorsqu'il sont connectés ils sont directement enregistré dans la liste,
une fois ceci effectué, à l'aide des plusieurs event, ont annule toutes possibilités d'être vu, de dégâts et de se mouvoir.