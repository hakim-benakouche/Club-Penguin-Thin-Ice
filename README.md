# Club-Penguin-Thin-Ice

Club Penguin Thin Ice est un jeu d'arcade en mode console

## Consignes du jeu

Dans ce jeu, le personnage principal, ECEMAN, se déplace dans les 4 directions dans un univers
composé de glace fine, de murs, d'obstacles, de banquise épaisse, de tunnels, d'outils, de bonus, d'une
porte de sortie et... d'ennemis. En mode console, les blocs de glace fine, de murs, … ennemis seront
chacun représenté par un caractère.

À chaque fois qu’ECEMAN passe sur une case de glace, celle-ci se brise pour faire apparaître de l'eau
et empêcher tout passage deux fois sur la même case. ECEMAN doit passer par toutes les cases de
glace, fine (se brise au premier passage dessus) ou épaisse (se brise au deuxième passage dessus), et
terminer par la porte pour gagner le tableau suivant.


## Prise en main du jeu 

  ### Les caractères

| Attribution  | Nom de la classe | Caractère
| ------------- | ------------- | ------------- |
| ECEMAN  | Penguin | P |
| Case vide  | Empty | . |
| Mur  | Wall | # |
| Eau  | Path | O |
| Glace fine  | Path | x |
| Glace épaisse  | Empty | X |
| Porte  | PathMapEnd | W |
| Tondeuse  | Mower | M |
| Brique de mur  | IceBlock | I |
| Destination brique de mur  | PathUnbreakable | i |
| Potion de légèreté  | PotionLightness | L |
| Potion de lourdeur  | PotionHeaviness | H |
| Tunnel  | Tunnel | T |
| Ennemi déplacement horizontale  | Ennemy | E |
| Ennemi déplacement verticale | Ennemy | e |

### Le calcul du score 

score = nombre de glace brisée + compteur de temps +  bonus niveau terminé (10 * numéro du niveau)  

Compteur de temps :
  * Pour chaque map on a un temps "moyen" (en secondes)
  * On a +1 ou -1 de bonus ou malus pour chaque seconde de moins ou de plus par rapport à ce temps moyen

Score calculé avec le respect des critères suivant : 
  * Score stocké dans un fichier lorsque le joueur quitte la partie.
  * Tout niveau non terminé n'est pas pris en compte dans les scores.
    
    

### Sauvegarde et chargement d'une partie

On sauvegarde le numéro du niveau + le score dans un fichier csv (ressources/saves/save.csv).
On peut charger une partie (on reprend le dernier niveau qui n'a pas été terminé).
Avec le respect des critères suivant :
  * Lorsque l’utilisateur commence à jouer (nouvelle partie ou reprise d’une ancienne partie), le tableau affiché est à refaire depuis le début.
  * Tout tableau terminé est sauvegardé dans un fichier pour permettre de continuer une partie sauvegardée (tableau suivant).
