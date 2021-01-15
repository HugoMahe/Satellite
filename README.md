# Projet Satellites & Balises
**Hugo MAHE** & **Gurvan LORANS-CANO**

## Introduction
Pour l'�laboration de ce projet qui consiste � la correction et � l'�volution du code nous avons impl�ment� diff�rentes fonctionnalit�s au sein des sources distribu�es au d�but du projet.

## Restructuration du code
Afin de pouvoir s'y retrouver plus facilement dans le projet, nous avons d�cid� de renommer les classes pour respecter une certaine nomenclature. Ainsi les classes de D�placement sont pr�fix�es par **_Depl_** et les classes d'�tats sont pr�fix�es par **_State_**. De plus nous avons restructur� l'arborescence par package afin de s'y retrouver plus facilement. De ce fait, nous avons les packages suivant : 
| Nom | Utilisation |
|--|--|
| deplacement | stocke la classe Deplacement et ses sous-classes |
| deplacement.balise | stocke la classe DeplBalise ainsi que ses sous-classes |
| event | Stocke tous les diff�rents �v�nements compris au sein du syst�me |
| listener | Va stocker les diff�rents listener qui peuvent �tre utilis�s dans le syst�me |
| model | Va stocker les classes "model" : e.g Satellite, Balise, ElementMobile, ... 
| simulation | Contient les classes utilis�es pour la simulation eg. les �l�ments graphique et la classe simulation | 
| state | Contient tous les diff�rents �tats utilis�s dans le syst�me | 


## Graphique

### GrDescription
### ... 

## Correction des concepts

### Gestion des �tats (State)
Certaines balises ne redescendaient pas suite � leurs synchronisation. Le bug a �t� rep�r� gr�ce au debug possible par l'interface graphique. Ainsi, nous avons pu rep�r� que ceci �tait d� � un probl�me de gestion d'�tat. Les balises continuait de collecter des donn�es lors de la plong�e.
Suite � ce constat, nous avons d�cid� de mettre en place une notion de state au lieu d'un attribut pour respecter au mieux l'OCP ainsi on peut facilement modifier le comportement d'une balise sans avoir � modifier/ajouter des attributs au sein de la classe balise. Nous avons donc 2 �tats : 
| Nom | Utilisation |
|--|--|
| StateCollect | Etat dans lequel la balise va r�colter ses donn�es |
| StateSynchronisation | Etat dans lequel la balise va remonter � la surface, attendre sa synchronisation, se synchroniser puis replonger � la profondeur � laquelle elle �tait avant sa remont�e |

StateSynchronisation va ainsi stocker le d�placement initial et la profondeur de la balise pour les r�-affecter � sa sortie

### DeplSynchronisation ne se d�place pas
La classe DeplSynchronisation ne concernait en rien un d�placement. L'objectif de cette classe �tait la gestion de la synchronisation. Ceci pose un probl�me de conception. Nous avons donc d�cid� de supprim� cette classe et d'impl�menter la classe DeplStandBy pour la remplacer.
En effet lors de la synchronisation une balise ne doit plus se d�placer. De plus le DeplStandBy �tait une notion du cahier des charges qui manquait � l'archive original. 
La gestion de la synchronisation quand � elle a �t� d�port�e dans la classe StateSynchronisation car ses actions concernaient le comportement de la balise ce qui par d�finition correspond � un �tat.
 
## Un Manager trop d�pendant

Un autre soucis majeur de la conception faites dans l'archive original concernait le DIP non respect� au sein du Manager. En effet, celui-ci se reposait sur des �l�ments **concret** tel que _Balise_ et _Satellite_ au lieu de se reposer sur des �l�ments **abstrait**. La correction a donc �t� �labor�e en 2 temps.

### Gestion du tick
Le manager se reposant sur les 2 classes concr�te du syst�me nous avons corrig� ceci pour le tick. Ainsi nous avons supprim� les listes concr�te pour les remplacer par des listes abstraites. 

Ainsi : 
```java
ArrayList<Satelitte> sats = new ArrayList<Satelitte>();
ArrayList<Balise> bals = new ArrayList<Balise>();
```
devient 
```java
Set<ElementMobile> elMobs = new HashSet<>();
```

et les m�thodes associ�es � l'ajout d'objet et de leurs ticks passe de : 
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
� simplement : 
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

On voit clairement ici qu'en plus de respecter le DIP, on simplifie et r�duite le code. Car nous agirons exactement de la m�me mani�re quelque soit l'�l�ment concret pour le tick �tant donn� que cette m�thode est incluse dans l'abstraction ElementMobile.

### Un abonnement trop concret
La seconde partie du Manager concernait la gestion de l'abonnement. Pour info nous avions les m�thodes suivantes : 
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

On voit ici qu'on utilise des �l�ments concret. Ceci pose donc probl�me au niveau du **DIP**. Nous devrions avoir des m�thodes qui se reposent uniquement sur **ElementMobile**, la classe abstraite.

Afin de pouvoir se reposait sur cette abstraction nous mettons donc en place un Double-Dispatch. Pour ce faire, dans ElementMobile nous d�finissons 2 m�thodes qui sont : 

#### Modification du mod�le
![Diagramme ](./diagramme-classe-double-dispatch.png)
#### S�quence d'appel de m�thode
*Pour une balise s'abonnant aux d�placements d'un satellite*
```mermaid
sequenceDiagram
ElementMobile(Balise) ->> Manager: checkSynchronisation(element)
Manager ->> ElementMobile(Satellite): checkReceiverSynchro(element: ElementMobile)
ElementMobile(Satellite) ->> ElementMobile(Balise): checkSatelliteSynchro(satellite: Satellite)
Note right of ElementMobile(Satellite): L'appel de la m�thode checkReceiverSynchro<br/> se fait pour tous les �l�ments mobiles enregistr� dans le Manager.
ElementMobile(Balise) ->> ElementMobile(Balise): satellite.registerListener(SatelliteMoved.class, this)
ElementMobile(Balise) -->> Manager: 
```

*Si l'�l�ment secondaire analys� �tait du type Balise (liaison Balise-Balise)* 
```mermaid
sequenceDiagram
ElementMobile(Balise) ->> Manager: checkSynchronisation(element)
Manager ->> ElementMobile(Balise2): checkReceiverSynchro(element: ElementMobile)
ElementMobile(Balise2) -->> Manager: 
```

Comme on peut le voir dans le diagramme ci-dessus le double dispatch n'a aucun impact sur les liaisons qui n'ont pas lieu d'�tre. En effet la balise n'�tant pas un receveur possible, sa m�thode `checkReceiverSynchro` n'est pas surcharg� et n'a donc aucun effet sur le programme (m�thode vide)

#### Explications textuelles
```java
	public void checkReceiverSynchro(ElementMobile other) {}

	public void checkSatelliteSynchro(Satelitte satelitte) {}
```

Ici `checkReceiverSynchro` sera la m�thode non d�terministe. Ainsi quand on l'appelle on aura aucune id�e de l'�l�ment concret  qui sera utilis� pour appel� la m�thode. De plus nous aurons aucun moyen de savoir le type concret du param�tre other. 

Nous avons donc 2 inconnues. Pour d�terminer une des 2 nous allons pouvoir surcharger la m�thode `checkReceiverSynchro` dans Satellite. Ainsi dans le code sur la m�thode surcharg� nous saurons que le `this` correspond � une instance de Satellite. 

Dans le code de cette surcharge nous appellerons donc la m�thode `checkSatelliteSynchro`  en passant this en param�tre.

Ainsi nous aurons qu'� surcharger la m�thode `checkSatelliteSynchro` pour indique aux classes concr�te qui doivent �mettre aux satellites. 

De ce fait, dans `Balise` nous surchargeons donc `checkSatelliteSynchro`

Ainsi dans le manager nous n'aurons qu'� impl�menter la m�thode suivante : 

```java
public void checkSynchronisation(ElementMobile element) {
	for (ElementMobile elMob : this.elMobs) {
		elMob.checkReceiverSynchro(element);
	}
}
```
Comme on peut le voir le param�tre est abstrait et le type de la liste parcourue �galement.

A noter que pour `unregister` un listener, nous avons utilis� exactement le m�me m�canisme mais avec des m�thodes ayant pour suffixe : `SynchroDone`