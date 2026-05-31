<h6> NB!
  This repository contains a copy of the backend code from a team project done at university. The original code is in a privat gitlab repository.
  Below is a copy of the Readme file for the project in german ( English Info at the end). 
  Since I worked in the backend, I only incuded that code. The code was uploaded with permission of the team. Info about tean below.
</h6>

</p>
<h1>HTWahl-O-Mat – Backend and Frontend</h1>


<!-- –––––––––– ÜBERBLICK  –––––––––– -->

<h2 id="ueberblick">Überblick</h2>

<p>
  Studierende der HTW können mit diesem Wahl-O-Mat, eineƒr webbasierten Anwendung, vorgegebene Aussagen zu hochschulpolitischen Themen bewerten, indem sie Punkte vergeben. <br>

  Die Bewertungen werden mit den Positionen von Kandidat*innen und Listen verglichen, um Übereinstimmungen sichtbar zu machen.<br>
  Auf diese Weise erhalten Studierende einen Überblick darüber, welche Personen oder Listen ihren eigenen Ansichten am nächsten stehen. <br> 
  Ziel der Anwendung ist es, eine einfache und verständliche Orientierungshilfe für die Hochschulwahlen bereitzustellen.
</p>

<br>

<details open>
  <summary> Zusätliche Features </summary> 

  <hr style="opacity:0.2">
  <div style="margin-left: 1.5em;">
    <h4>Kandidat</h4>
    <p> Das System bietet registrierten Kandidat*innen die Möglichkeit, eigene Positionen zu den vorgegebenen Aussagen zu hinterlegen. </p>
    <p> Nach der Registrierung können Kandidat*innen ihre Bewertungen speichern und sich einer oder mehreren Wahllisten zuordnen. Die eingegebenen Daten werden persistent in der Datenbank gespeichert und vom Backend verwaltet. </p>
    <p> Auf diese Weise werden Kandidat*innen strukturiert im System erfasst und für die weitere Verarbeitung innerhalb der Anwendung bereitgestellt. </p>
  </div>
  <hr style="opacity:0.2">
  <div style="margin-left: 1.5em;">
    <h4>Admin</h4>
    <p> Administrator*innen verfügen über erweiterte Rechte zur systemweiten Steuerung und Pflege der Daten. Dazu gehören unter anderem das Anlegen und Verwalten von Gremien, das Hinzufügen und Bearbeiten von Aussagen sowie die Validierung von Wahllisten. </p>
    <p> Darüber hinaus können Administrator*innen Kandidat*innen und Listen löschen oder anpassen und erhalten einen umfassenden Überblick über die im System gespeicherten Daten.</p>
    <p> Der Zugriff auf diese Funktionen ist ausschließlich autorisierten Benutzer*innen vorbehalten. </p>
  </div>
  <hr style="opacity:0.2">

<br>
</details>



<!-- –––––––––– SCREENSHOTS –––––––––– -->

<details open>
<summary>
Screenshots
</summary> <br />

<p align="center">
<img width="49%"  alt="startseite" src="https://github.com/user-attachments/assets/43db17ea-1308-4261-af2d-7b5983bae422" />
<img width="49%"  alt="start" src="https://github.com/user-attachments/assets/6967c431-0b56-476f-bedd-25f0f365b1b4" />  
</p>

<p align="center">
<img width="49%"  alt="fragebogen" src="https://github.com/user-attachments/assets/507f6085-e3fe-467e-aba2-6d10ea4cb739" />
<img width="49%"  alt="register" src="https://github.com/user-attachments/assets/3d846e80-d210-4227-9c67-67a7d72398af" />
</p>


<p align="center">
 <img width="49%"  alt="fachbereich" src="https://github.com/user-attachments/assets/9c1a0178-ebe6-49ac-9e6a-bf27a9e267b7" />
 <img width="49%"  alt="ergebnis" src="https://github.com/user-attachments/assets/0cbe2c41-5d4d-4407-913c-a66e6034374a" />
</p>

<br><br>
</details>

<br>

<hr>

<!-- –––––––––– TECHNOLOGIEN –––––––––– -->

<h2 id="technologien">Angewandte Technologien</h2>

<ul>
  <li><strong>Frontend:</strong> JavaScript (Angular)</li>
  <li><strong>Backend:</strong> Spring Boot</li>
  <li><strong>Programmiersprache:</strong> Java</li>
  <li><strong>Datenbank:</strong> PostgreSQL</li> <li><strong>API:</strong>
  REST</li> <li><strong>API-Dokumentation:</strong> OpenAPI / Swagger</li>
</ul>

<hr>





<!-- –––––––––– TEAM –––––––––– -->

<h3 id="authors">Autor*innen</h3>

<p>Dieses Projekt wurde im Rahmen eines Hochschulprojekts entwickelt von:</p> 

<br>

<div style="display: flex; flex-wrap: wrap; justify-content: center; gap: 30px;">

  <div style="text-align: center;">
    <p><strong>Nicole Eisner</strong><br> <p style="font-weight: 300;">(Scrum-Master)</p>
  </div>

  <div style="text-align: center;">
    <p><strong>Danusika Kirupakaran</strong><br> <p style="font-weight: 300;">(Product Owner)</p>
  </div>
  
  <div>
<p style="font-weight: 300;">Developer: </p>
  </div>
  
  <div style="text-align: center;">
    <p><strong>Celine Dumke, Luise Tabatt, Natalia Schmidt, Gamze Bektas, Cemre Karsli,Delal Erdogan, Maeva Nguemezi </strong><br>
  </div>
<br>
<hr>

<blockquote>
<p>
  Dieses Repository enthält das <strong>Backend</strong> des HTWahl-O-Mat.<br>
  Das zugehörige Frontend befindet sich in einem separaten Repository:
</p>

<p>
  <a href="https://gitlab.rz.htw-berlin.de/s0575626/wahlomat-frontend" style="color: #76b900;">
    HTWahl-O-Mat Frontend
  </a>
</p>

<p>
  Das Backend ist mit einer internen PostgreSQL-Datenbank verbunden.<br>

  Für den Betrieb ist eine Verbindung zum HTW-Netz oder eine aktive
  VPN-Verbindung erforderlich.
</p>
</blockquote>


<h2 id="code">Code-Dokumentation</h2>

<p>
  Der Source-Code ist mit <strong>JavaDoc</strong> dokumentiert, um Wartung und
  Weiterentwicklung zu erleichtern.
</p>

<p>

<br>
<hr>
<br>



  
</p>
<h2  id="english"> English version of readme </h2>

This repository contains the backend of the Wahl-O-Mat (Full-Stack) project. 
This is being developed for university intern elections. Last year only 5% of students voted in the elections. With this we hope to simplify the voting process and therefore encourage a higher number of people to give in their vote.
The voter and candidates can both give ratings to questions. Based on those numbers the fitting top candidates per party are given to the voters. 

The application also provides secure authentication, reset password, candidate profiles, and result calculations.

 <h2>Team</h2>
<h4> Product Owner:</h4>
Danusika Kirupakan 

<h4>Scrum Master: </h4>
Nicole Eisner

<h4>Developer: </h4>
Celine Dumke,
Luise Tabatt,
Natalia Schmnidt,
Gamze Bektas,
Cemre Karsli,
Delal Erdogan,
Maeva Nguemezi


 <h2>Features</h2>
 
- User registration and login (voters & candidates)
- Secure authentication and authorization (JWT token)
- Voting process with validation
- Automatic vote counting and result calculation
- RESTful API for frontend integration
- API documentation with Swagger
- Code documentation with Javadoc
- Unit tests with JUnit


<h2>Tech Stack</h2>

- Language:Java
- Framework: Spring Boot
- Database: PostgreSQL
- ORM: Spring Data JPA / Hibernate
- API Documentation**: Swagger (OpenAPI)
- Documentation: Javadoc
- Build Tool: Maven
- Testing: Junit and Integration tests with h2
