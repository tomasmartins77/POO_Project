pasta = GrafoPack
pastaMain = Main

all:ChangeFile java jarCreate

teste: 
	rm META-INF/MANIFEST.MF
	touch META-INF/MANIFEST.MF

ChangeFile:
	
	
	echo "Manifest-Version: 1.0" > META-INF/MANIFEST.MF
	echo "Created-By: 11.0.18 (Ubuntu)">> META-INF/MANIFEST.MF
	echo "Main-Class: PrincipalMain.Main" >> META-INF/MANIFEST.MF

java:
	javac ACO/*.java Main.java DSS/*.java GrafoPack/*.java LeitorDeArgs/*.java

jarCreate:
	jar cmf META-INF/MANIFEST.MF executavel.jar ACO/*.class Main.class DSS/*.class GrafoPack/*.class LeitorDeArgs/*.class 

runJava:
	java -jar executavel.jar -f file.txt