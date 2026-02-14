<h1>HTWahl-O-Mat – Backend</h1>

<br>

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
  <li><a href="#code" style="color: #76b900;">Code-Dokumentation</a></li>
</ol>

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

<hr>

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
