var tblUsers = document.getElementById('tbl_users_list');
  var databaseRef = firebase.database().ref('trabalho/');
  var rowIndex = 1;

  var op = '<option value="vazio">Selecionar ID</option>';

  databaseRef.once('value', function(snapshot) {
    snapshot.forEach(function(childSnapshot) {
   var childKey = childSnapshot.key;
   var childData = childSnapshot.val();
  op += '<option value = "'+childKey+'" >'+childKey+'</option>';
   

   var row = tblUsers.insertRow(rowIndex);
   var cellId = row.insertCell(0);
   var cellNome = row.insertCell(1);
   var cellAutor = row.insertCell(2);
   var cellGitHub = row.insertCell(3);
   var cellLinguagem = row.insertCell(4);
   var cellUsuario = row.insertCell(5);
   var cellSenha = row.insertCell(6);

   cellId.appendChild(document.createTextNode(childKey));
   cellNome.appendChild(document.createTextNode(childData.nome));
   cellAutor.appendChild(document.createTextNode(childData.autor));
   cellGitHub.appendChild(document.createTextNode(childData.github));
   cellLinguagem.appendChild(document.createTextNode(childData.linguagem));
   cellUsuario.appendChild(document.createTextNode(childData.usuario));
   cellSenha.appendChild(document.createTextNode(childData.senha));


   rowIndex = rowIndex + 1;
    });

    document.getElementById('id').innerHTML = op;

  });
  
  function novo(){

        document.getElementById('nomeTrabalho').value = "Nome do trabalho";
        document.getElementById('nomeAutor').value = "Nome do autor";
        document.getElementById('gitHub').value = "Seu GitHub";
        document.getElementById('linguagem').value = "Sua linguagem de programação";
        document.getElementById('nomeUsuario').value = "Nome de usuário";
        document.getElementById('senhaUsuario').value = "Senha de usuário";
  }

  function selecao(valor){
    var uid = valor;
    console.log(document.getElementById('id').selectedIndex);

    if(document.getElementById('id').selectedIndex != 0){

        var fb = firebase.database().ref().child('trabalho/'+uid);
        fb.on('value', function(snapshot) {
          var dados = snapshot.val();
      
          document.getElementById('nomeTrabalho').value = dados.nome;
          document.getElementById('nomeAutor').value = dados.autor;
          document.getElementById('gitHub').value = dados.github;
          document.getElementById('linguagem').value = dados.linguagem;
          document.getElementById('nomeUsuario').value = dados.usuario;
          document.getElementById('senhaUsuario').value = dados.senha;
      
        });
      }else{
        document.getElementById('id').selectedIndex = 0;
        document.getElementById('nomeTrabalho').value = "Nome do trabalho";
        document.getElementById('nomeAutor').value = "Nome do autor";
        document.getElementById('gitHub').value = "Seu GitHub";
        document.getElementById('linguagem').value = "Sua linguagem de programação";
        document.getElementById('nomeUsuario').value = "Nome de usuário";
        document.getElementById('senhaUsuario').value = "Senha de usuário";

      }


  }

  function salvar(){

    var nome = document.getElementById('nomeTrabalho').value;
    var aut = document.getElementById('nomeAutor').value;
    var git = document.getElementById('gitHub').value;
    var ling = document.getElementById('linguagem').value;
    var user = document.getElementById('nomeUsuario').value;
    var senha = document.getElementById('senhaUsuario').value;

    var uid = firebase.database().ref('trabalho').push().key;

   firebase.database().ref('trabalho/'+uid).update({
        id : uid,
        nome : nome,
        autor : aut,
        github : git,
        linguagem : ling,
        usuario : user,
        senha : senha
   });
   
   alert('Usuário criado com sucesso!');
   window.location.replace("index.html");
  }
  
  function atualizar(){

   if(document.getElementById('id').selectedIndex != 0){ 
        var nome = document.getElementById('nomeTrabalho').value;
        var aut = document.getElementById('nomeAutor').value;
        var git = document.getElementById('gitHub').value;
        var ling = document.getElementById('linguagem').value;
        var user = document.getElementById('nomeUsuario').value;
        var senha = document.getElementById('senhaUsuario').value;
    
        var uid = document.getElementById('id').selectedIndex.value;
    
       firebase.database().ref('trabalho/'+uid).update({
            id : uid,
            nome : nome,
            autor : aut,
            github : git,
            linguagem : ling,
            usuario : user,
            senha : senha
       });
       
       alert('Usuário atualizado com sucesso!');
       window.location.replace("index.html");
       }else{
        alert('Selecione um ID de Usuário para alterar.')
       } 
  }
  
  function deletar(){
   if(document.getElementById('id').selectedIndex != 0){ 
       var uid = document.getElementById('id').value;
      
       firebase.database().ref('trabalho/' + uid).remove();
       alert('Usuário deletado com sucesso!');
       window.location.replace("index.html");
    }else{
      alert('Selecione um ID de Usuário para deletar.');
    }
  }
  