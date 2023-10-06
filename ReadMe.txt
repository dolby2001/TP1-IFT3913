# TP1-IFT3913
Abderrezak Agsous 20189331
Olivier Langelier-Mainville 20220165

https://github.com/dolby2001/TP1-IFT3913

Compilation:
Afin de compiler notre code, il faut tout simplement utiliser MVN. Premièrement, il faut se diriger sur le répertoire du jfreechart-master
avec cd freechart-master et ensuite compiler le projet en utlisant mvn compile.

Éxecution:
Pour éxecuter nos programmes, il faut se diriger vers le répertoire jfreechart-master\target\classes et tout simplement utiliser la commande désiré. 
Voici les commandes disponibles:
java TLocCalculator <chemin-de-l'entrée>
java TassertCalculator <chemin-de-l'entrée>
java Tls -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée>
java Tropcomp -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée> <seuil>

Notre code pour les 4 programmes se retrouve dans le répertoire <jfreechart-master\src\main\java\TP1-Tests>. De cette facon, le projet se retrouve dans un seul fichier pour rendre la compilation plus facile
Nous avons implémenté notre code de façon que toutes sorties csv se retrouve dans <jfreechart-master\target\classes\org>