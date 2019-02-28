package gruposd.sdfirebase;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentCadastro extends Fragment {

    private View                view;

    private ImageButton         btSalvar, btAddDisciplina;
    private EditText            etNome, etAutor, etLinguagem, etGitHub, etUsuario, etSenha;

    private Trabalho            trabalho;

    private DatabaseReference   databaseReference;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager     fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_cadastro, container, false);

        btSalvar        = (ImageButton) view.findViewById(R.id.btSalvarTrabalho);
        btAddDisciplina = (ImageButton) getActivity().findViewById(R.id.btAddDisciplina);
        etNome          = (EditText)    view.findViewById(R.id.etNome);
        etAutor         = (EditText)    view.findViewById(R.id.etAutor);
        etLinguagem     = (EditText)    view.findViewById(R.id.etLinguagem);
        etGitHub        = (EditText)    view.findViewById(R.id.etGitHub);
        etUsuario       = (EditText)    view.findViewById(R.id.etUsuario);
        etSenha         = (EditText)    view.findViewById(R.id.etSenha);

        btAddDisciplina.setVisibility(View.INVISIBLE);
        btAddDisciplina.setClickable(false);

        databaseReference = FirebaseDatabase.getInstance().getReference("trabalho");

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trabalho = new Trabalho();
                trabalho.setId(databaseReference.push().getKey());
                trabalho.setNome(etNome.getText().toString());
                trabalho.setAutor(etAutor.getText().toString());
                trabalho.setLinguagem(etLinguagem.getText().toString());
                trabalho.setGithub(etGitHub.getText().toString());
                trabalho.setUsuario(etUsuario.getText().toString());
                trabalho.setSenha(etSenha.getText().toString());

                if(    !TextUtils.isEmpty(trabalho.getNome())
                    && !TextUtils.isEmpty(trabalho.getAutor())
                    && !TextUtils.isEmpty(trabalho.getLinguagem())
                    && !TextUtils.isEmpty(trabalho.getGithub())
                    && !TextUtils.isEmpty(trabalho.getUsuario())
                    && !TextUtils.isEmpty(trabalho.getSenha())
                        ) {

                    databaseReference.child(trabalho.getId()).setValue(trabalho);

                    Toast.makeText(getActivity(), "Trabalho cadastrado", Toast.LENGTH_SHORT).show();

                    btAddDisciplina.setVisibility(View.VISIBLE);
                    btAddDisciplina.setClickable(true);

                    fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.containerPrincipal, new FragmentInicial(), "FragmentInicial");
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(getActivity(), "Preencha todos os campos para salvar", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}
