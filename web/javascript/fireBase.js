// Initialize Firebase
  var config = {
    apiKey: "AIzaSyB_AKoKWLfJSp6BQMPg0_s8kChbpri2moQ",
    authDomain: "sd-firebase-e1ab4.firebaseapp.com",
    databaseURL: "https://sd-firebase-e1ab4.firebaseio.com",
    projectId: "sd-firebase-e1ab4",
    storageBucket: "sd-firebase-e1ab4.appspot.com",
    messagingSenderId: "96935726345"
  };
  firebase.initializeApp(config);


var admin = require("firebase-admin");

var serviceAccount = require("D:/CSI/Dcpl/SD/Trab1/Meu/serviceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://sd-firebase-e1ab4.firebaseio.com"
});