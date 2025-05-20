# BankingApp
Repozitoriul "BankingApp" conține codul sursă al proiectului aplicației desktop bancare pentru servicii de casierie. Proiectul este scris utilizând limbajul Java și framework-ul interfeței grafice Swing. Aplicația a fost concepută pentru utilizatori clienți ai băncilor care să aibă oportunitatea gestionării contului nemijlocit pe calculator. Aplicația este distribuită și poate fi rulată pe orice sistem de operare: Windows, MacOS, Linux, alte sisteme Unix. Pentru a asigura confort și ușurință în navigare urmărim obiectivul proiectării unei interfețe simple și consistente cu design atractiv și plăcut de utilizat. Datele utilizatorilor sunt păstrate într-o bază de date relațională open-source (MySQL) ce constituie o soluție robustă și flexibilă pentru stocarea și accesarea informațiilor. Dezvoltarea aplicației bancare pe desktop furnizează o categorie nouă de clienți, stimulează accesibilitatea securizată, protecție stratificată și atitudine responsabilă față de credențialele clienților.

Cerințe alternative:
---
* Kitul de dezvoltare Java - [Java Downloads | Oracle](https://www.oracle.com/java/technologies/downloads/ "Link oficial către Kitul de dezvoltare Java")
* Java pentru Windows - [Download Java for Windows](https://www.java.com/en/download/ "Link oficial către Java 8 soluția utilizatorilor simpli")

Cum să construiți aplicația folosind mediul integrat de dezvoltare IntelliJ IDEA:
1. Compilați proiectul (Build → Build Project)
2. Creeați un artifact pentru fișierul JAR (File → Project Structure → Project Settings(Artifact) → (+) → Add (JAR) → From modules with dependencies → Module: BankingApp, Main Class: AppLauncher, JAR files from libraries (extract to the target JAR) → OK → Project Structure (Include in project build) → Apply → OK → Build → Build Artifacts → Build Artifact (BankingApp:jar) → Action (Build))
3. Accesați directorul standard pentru aplicația construită (BankingApp/out/artifacts/BankingApp_jar) și găsiți aplicația `BankingApp.jar`

Build-ul aplicației poate fi construit, sau descărcat din repozitoriul curent. Aplicația este deplin funcțională dacă lansați `MySQL Server` încorporat în suite de dezvoltare precum (MySQL Installer + MySQL  Workbench), XAMPP, WAMP Server, MAMP, Laragon, AMPPS, Open Server, etc.

În build-ul atașat acestui repozitoriu parola pentru baza de date `bankapp` este: `12345689`

Dacă optați să personalizați conexiunea la baza de date atunci este nevoie să construiți manual aplicația. Configurările relevante se fac în fișierul `config.properties`

Imagini ale aplicației:

![Imaginea aplicației bancare prezintă un design structurat, cu un panou principal ce oferă acces rapid la funcționalități esențiale precum gestionarea conturilor, tranzacții recente, detalii financiare și opțiuni de administrare a utilizatorului][animated_bank_app]

[animated_bank_app]: https://github.com/watchfoxie/BankingApp/raw/main/thesis_res/19.%20bankapp_nav.gif

© 2025 Dezvoltat și proiectat de Tocana Marian, Adela Gorea. USARB