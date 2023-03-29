Application mobile de présence :

REQUIS : 
- connecter le seveur pour pouvoir faire deux requêtes : 
  - http://192.168.10.11/lpro/api/login.php?key=iot1235&username=Tracy&password=4537
  - http://192.168.10.11/lpro/api/qrcode-sign.php?key=iot1235&token=e8147e7f62c9307276567064eec13817&qrcode=....


- activité login : connexion avec l'identifiant Tracy et 4537, récupère un object JSON contenant un token
- activité main avec une navigation drawer et plusieurs fragements :
  - fragement home : contient un scanner de qr code
    - récuperer le code du qr code et le token pour faire la 2e requête et signaler la présence
  - deux autres fragments inutiles pour l'instant