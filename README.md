<h1>HTWahl-O-Mat – Backend</h1>
<br>

<h2>English version below!</h2>

<h3>Inhaltsverzeichnis</h3>
<ol>
  <li><a href="#ueberblick" style="color: #76b900;">Überblick</a></li> <li><a
  href="#technologien" style="color: #76b900;">Angewandte Technologien</a></li>
  <li>
    <a href="#installation" style="color: #76b900;">Anleitung zur
    Installation</a>
    <ol type="I">
      <li><a href="#voraussetzungen" style="color:
      #76b900;">Voraussetzungen</a></li> <li><a href="#database" style="color:
      #76b900;">Datenbank-Abhängigkeit</a></li> <li><a href="#vorgehen"
      style="color: #76b900;">Vorgehen</a></li>
    </ol>
  </li>
  <li><a
        href="#code" style="color: #76b900;">Code-Dokumentation</a></li>
 <li>  <a
        href="#english" style="color: #76b900;">English version </a>
</li>
</ol>
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

<hr>

<h2 id="ueberblick">Überblick</h2>

<p>
  Studierende der HTW können mit diesem Wahl-O-Mat, einer webbasierten
  Anwendung, vorgegebene Aussagen zu hochschulpolitischen Themen bewerten, indem
  sie Punkte vergeben. <br>
      
  Die Bewertungen werden mit den Positionen von Kandidat*innen und Listen
  verglichen, um Übereinstimmungen sichtbar zu machen.<br>
      
  Auf diese Weise erhalten Studierende einen Überblick darüber, welche Personen
  oder Listen ihren eigenen Ansichten am nächsten stehen. <br>
      
  Ziel der Anwendung ist es, eine einfache und verständliche Orientierungshilfe
  für die Hochschulwahlen bereitzustellen.
</p>

<br>

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

<br>

<hr>

<h2 id="technologien">Angewandte Technologien</h2>

<ul>
  <li><strong>Programmiersprache:</strong> Java</li>
  <li><strong>Framework:</strong> Spring Boot</li>
  <li><strong>Datenbank:</strong> PostgreSQL</li> <li><strong>API:</strong>
  REST</li> <li><strong>API-Dokumentation:</strong> OpenAPI / Swagger</li>
</ul>


<h2 id="installation">Anleitung zur Installation</h2>

<h3 id="voraussetzungen">Voraussetzungen</h3>

<p>Für die lokale Ausführung des Frontends werden folgende Komponenten
benötigt:</p>

<ul>
  <li>Git</li> <li>Java JDK</li> <li>Maven (oder Maven Wrapper)</li>
  <li>VPN-Zugang zur HTW</li>
</ul>

<br>

<h3 id="database">Datenbank-Abhängigkeit</h3>

<p>
  Das Backend nutzt eine PostgreSQL-Datenbank, die auf einem internen Server
  betrieben wird.
</p>

<blockquote>
  <p>
    Ein Zugriff auf die Datenbank ist nur aus dem HTW-Netz oder über eine aktive
    VPN-Verbindung möglich.<br> Ein direkter Zugriff durch externe Nutzer ist
    nicht vorgesehen.
  </p>
</blockquote>

<p>
Ohne aktive Netzwerkverbindung zur HTW kann das Backend nicht vollständig
betrieben werden.
</p>

<br>

<h3 id="vorgehen">Vorgehen</h3>

<ol>
  <li>
    <strong>Repository klonen</strong> <pre><code>git clone
    https://gitlab.rz.htw-berlin.de/s0575626/wahlomat-backend</code></pre>
  </li>

  <li>
    <strong>Projekt bauen</strong> <pre><code>./mvnw clean install</code></pre>
  </li>

  <li>
    <strong>Backend starten</strong> <pre><code>./mvnw
    spring-boot:run</code></pre>
  </li>

  <li>
    <strong>API prüfen (Swagger)</strong>
    <pre><code>http://localhost:8080/swagger-ui/index.html</code></pre>
  </li>

  <li>
    <strong>Frontend installieren</strong>
    <p>
  <a href="https://gitlab.rz.htw-berlin.de/s0575626/wahlomat-frontend" style="color: #76b900;">
    HTWahl-O-Mat Frontend
  </a>
</p>

  </li>

</ol>

<br>

<blockquote>
  <p>
    Bei erfolgreicher Installation läuft das Backend gemeinsam mit dem Frontend
    als Anwendung im Browser über 'http://localhost:4200'
  </p>
</blockquote>

<br>

<hr>

<h2 id="code">Code-Dokumentation</h2>

<p>
  Der Source-Code ist mit <strong>JavaDoc</strong> dokumentiert, um Wartung und
  Weiterentwicklung zu erleichtern.
</p>

<p>

  
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
