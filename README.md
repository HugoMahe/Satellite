# Projet Satellites & Balises
**Hugo MAHE** & **Gurvan LORANS-CANO**

## Introduction
Pour l'élaboration de ce projet qui consiste à la correction et à l'évolution du code nous avons implémenté différentes fonctionnalités au sein des sources distribuées au début du projet.

## Restructuration du code
Afin de pouvoir s'y retrouver plus facilement dans le projet, nous avons décidé de renommer les classes pour respecter une certaine nomenclature. Ainsi les classes de Déplacement sont préfixées par **_Depl_** et les classes d'états sont préfixées par **_State_**. De plus nous avons restructuré l'arborescence par package afin de s'y retrouver plus facilement. De ce fait, nous avons les packages suivant : 
| Nom | Utilisation |
|--|--|
| deplacement | stocke la classe Deplacement et ses sous-classes |
| deplacement.balise | stocke la classe DeplBalise ainsi que ses sous-classes |
| event | Stocke tous les différents évènements compris au sein du système |
| listener | Va stocker les différents listener qui peuvent être utilisés dans le système |
| model | Va stocker les classes "model" : e.g Satellite, Balise, ElementMobile, ... 
| simulation | Contient les classes utilisées pour la simulation eg. les éléments graphique et la classe simulation | 
| state | Contient tous les différents états utilisés dans le système | 


## Graphique

### GrDescription
### ... 

## Correction des concepts

### Gestion des états (State)
Certaines balises ne redescendaient pas suite à leurs synchronisation. Le bug a été repéré grâce au debug possible par l'interface graphique. Ainsi, nous avons pu repéré que ceci était dû à un problème de gestion d'état. Les balises continuait de collecter des données lors de la plongée.
Suite à ce constat, nous avons décidé de mettre en place une notion de state au lieu d'un attribut pour respecter au mieux l'OCP ainsi on peut facilement modifier le comportement d'une balise sans avoir à modifier/ajouter des attributs au sein de la classe balise. Nous avons donc 2 états : 
| Nom | Utilisation |
|--|--|
| StateCollect | Etat dans lequel la balise va récolter ses données |
| StateSynchronisation | Etat dans lequel la balise va remonter à la surface, attendre sa synchronisation, se synchroniser puis replonger à la profondeur à laquelle elle était avant sa remontée |

StateSynchronisation va ainsi stocker le déplacement initial et la profondeur de la balise pour les ré-affecter à sa sortie

### DeplSynchronisation ne se déplace pas
La classe DeplSynchronisation ne concernait en rien un déplacement. L'objectif de cette classe était la gestion de la synchronisation. Ceci pose un problème de conception. Nous avons donc décidé de supprimé cette classe et d'implémenter la classe DeplStandBy pour la remplacer.
En effet lors de la synchronisation une balise ne doit plus se déplacer. De plus le DeplStandBy était une notion du cahier des charges qui manquait à l'archive original. 
La gestion de la synchronisation quand à elle a été déportée dans la classe StateSynchronisation car ses actions concernaient le comportement de la balise ce qui par définition correspond à un état.
 
## Un Manager trop dépendant

Un autre soucis majeur de la conception faites dans l'archive original concernait le DIP non respecté au sein du Manager. En effet, celui-ci se reposait sur des éléments **concret** tel que _Balise_ et _Satellite_ au lieu de se reposer sur des éléments **abstrait**. La correction a donc été élaborée en 2 temps.

### Gestion du tick
Le manager se reposant sur les 2 classes concrète du système nous avons corrigé ceci pour le tick. Ainsi nous avons supprimé les listes concrète pour les remplacer par des listes abstraites. 

Ainsi : 
```java
ArrayList<Satelitte> sats = new ArrayList<Satelitte>();
ArrayList<Balise> bals = new ArrayList<Balise>();
```
devient 
```java
Set<ElementMobile> elMobs = new HashSet<>();
```

et les méthodes associées à l'ajout d'objet et de leurs ticks passe de : 
```java
public void addBalise(Balise bal) {
	bals.add(bal);
	bal.setManager(this);
}

public void addSatellite(Satelitte sat) {
	this.sats.add(sat);
	sat.setManager(this);
}

public void tick() {
	for (Balise b : this.bals) {
		b.tick();
	}
	for (Satelitte s : this.sats) {
		s.tick();
	}
}
```
à simplement : 
```java
public void addElement(ElementMobile elMob) {
	this.elMobs.add(elMob);
	elMob.setManager(this);
}

public void tick() {
	for (ElementMobile elMob : this.elMobs) {
		elMob.tick();
	}
}
```

On voit clairement ici qu'en plus de respecter le DIP, on simplifie et réduite le code. Car nous agirons exactement de la même manière quelque soit l'élément concret pour le tick étant donné que cette méthode est incluse dans l'abstraction ElementMobile.

### Un abonnement trop concret
La seconde partie du Manager concernait la gestion de l'abonnement. Pour info nous avions les méthodes suivantes : 
```java
public void baliseReadyForSynchro(Balise b) {
	for (Satelitte s : this.sats) {
		s.registerListener(SatelitteMoved.class, b);
	}
}
public void baliseSynchroDone(Balise b) {
	for (Satelitte s : this.sats) {
		s.unregisterListener(SatelitteMoved.class, b);
	}
}
``` 

On voit ici qu'on utilise des éléments concret. Ceci pose donc problème au niveau du **DIP**. Nous devrions avoir des méthodes qui se reposent uniquement sur **ElementMobile**, la classe abstraite.

Afin de pouvoir se reposait sur cette abstraction nous mettons donc en place un Double-Dispatch. Pour ce faire, dans ElementMobile nous définissons 2 méthodes qui sont : 

#### Modification du modèle
![Diagramme ](./diagramme-classe-double-dispatch.png)
#### Séquence d'appel de méthode
*Pour une balise s'abonnant aux déplacements d'un satellite*
```mermaid
sequenceDiagram
ElementMobile(Balise) ->> Manager: checkSynchronisation(element)
Manager ->> ElementMobile(Satellite): checkReceiverSynchro(element: ElementMobile)
ElementMobile(Satellite) ->> ElementMobile(Balise): checkSatelliteSynchro(satellite: Satellite)
Note right of ElementMobile(Satellite): L'appel de la méthode checkReceiverSynchro<br/> se fait pour tous les éléments mobiles enregistré dans le Manager.
ElementMobile(Balise) ->> ElementMobile(Balise): satellite.registerListener(SatelliteMoved.class, this)
ElementMobile(Balise) -->> Manager: 
```

*Si l'élément secondaire analysé était du type Balise (liaison Balise-Balise)* 
```mermaid
sequenceDiagram
ElementMobile(Balise) ->> Manager: checkSynchronisation(element)
Manager ->> ElementMobile(Balise2): checkReceiverSynchro(element: ElementMobile)
ElementMobile(Balise2) -->> Manager: 
```

Comme on peut le voir dans le diagramme ci-dessus le double dispatch n'a aucun impact sur les liaisons qui n'ont pas lieu d'être. En effet la balise n'étant pas un receveur possible, sa méthode `checkReceiverSynchro` n'est pas surchargé et n'a donc aucun effet sur le programme (méthode vide)

#### Explications textuelles
```java
	public void checkReceiverSynchro(ElementMobile other) {}

	public void checkSatelliteSynchro(Satelitte satelitte) {}
```

Ici `checkReceiverSynchro` sera la méthode non déterministe. Ainsi quand on l'appelle on aura aucune idée de l'élément concret  qui sera utilisé pour appelé la méthode. De plus nous aurons aucun moyen de savoir le type concret du paramètre other. 

Nous avons donc 2 inconnues. Pour déterminer une des 2 nous allons pouvoir surcharger la méthode `checkReceiverSynchro` dans Satellite. Ainsi dans le code sur la méthode surchargé nous saurons que le `this` correspond à une instance de Satellite. 

Dans le code de cette surcharge nous appellerons donc la méthode `checkSatelliteSynchro`  en passant this en paramètre.

Ainsi nous aurons qu'à surcharger la méthode `checkSatelliteSynchro` pour indique aux classes concrète qui doivent émettre aux satellites. 

De ce fait, dans `Balise` nous surchargeons donc `checkSatelliteSynchro`

Ainsi dans le manager nous n'aurons qu'à implémenter la méthode suivante : 

```java
public void checkSynchronisation(ElementMobile element) {
	for (ElementMobile elMob : this.elMobs) {
		elMob.checkReceiverSynchro(element);
	}
}
```
Comme on peut le voir le paramètre est abstrait et le type de la liste parcourue également.

A noter que pour `unregister` un listener, nous avons utilisé exactement le même mécanisme mais avec des méthodes ayant pour suffixe : `SynchroDone`