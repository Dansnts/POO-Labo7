# Laboratoire 2 - POO

**Auteurs :**

Dani Tiago **Faria dos Santos**

Antoine **Aubry**

## Exercice 1
### 1.
![partie1](Exercice1-1.png)

Nous sommes partis du principe qu'une médiathèque peut ne pas posséder de médias, et qu'elle ne possède pas encore d'abonnés. L'abonné, une fois inscrit, peut louer plusieurs ou aucun média.

### 2.
![partie2](Exercice1-2.png)

Simple, un pays est frontalier de zéro ou plusieurs autres pays.

### 3.
![partie3](Exercice1-3.png)

Un client possède un véhicule qui est pris en charge par le garage mandaté. Le mécanicien utilise des pièces de moto ou de voiture selon le besoin pour réparer le véhicule et travaille forcément pour un seul garage à la fois.

### 4.
![partie4](Exercice1-4.png)

Un paquebot contient des cabines habitées par les résidents. Les passagers participent aux activités sur le paquebot ou sur les sites. Les organisateurs, eux, sont des guides ou des animateurs. Sans guide, pas de visite, et sans animateur, pas d'animations, donc il faut au moins un de chaque pour organiser les activités.

### 5.
![partie5](Exercice1-5.png)

Un individu, une fois marié, est listé dans un mariage. Ici, le mariage a été pensé à deux. L'individu a un sexe et des parents, il peut ne pas avoir d'enfants, mais être l'enfant d'un parent avec d'autres enfants.

## Exercice 2
### Modélisation d’un éditeur

Nous sommes partis du principe que les livres sont liés aux éditeurs et aux éditions. La création de "Editeur" permet d'avoir un point commun pour les commandes et les droits d'auteur. Les droits d'auteurs dans "droitAuteur" permettent de faire un lien direct entre l'auteur et l'édition en question pour simplifier la somme à attribuer à l'auteur.

![partie1](Exercice2-1.png)

#### Implémentation
![partie1a](Exercice2-1-Impl.png)

### Modélisation d’une école

Pour ce cas-ci, nous avons décidé de faire trois colonnes : Professeur avec la sous-classe doyen et le département, Étudiant avec une liaison ternaire pour le groupe-orientation-trimestre, et le reste des employés avec "Admin".

Pour les salles, nous partons du principe que plusieurs leçons sont données dans une salle selon un horaire précis. Les leçons, elles, sont données dans une salle à un groupe et sont sous la responsabilité d'un enseignant.

![partie2](Exercice2-2.png)

#### Implémentation
![partie2a](Exercice2-2-Impl.png)
