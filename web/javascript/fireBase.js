// Initialize Firebase
  var config = {
    apiKey: "....",
    authDomain: "....",
    databaseURL: "....",
    projectId: "....",
    storageBucket: "....",
    messagingSenderId: "...."
  };
  firebase.initializeApp(config);


var admin = require("firebase-admin");

var serviceAccount = require("D:/CSI/Dcpl/SD/Trab1/Meu/serviceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "...."
});
