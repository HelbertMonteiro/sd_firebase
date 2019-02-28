package gruposd.sdfirebase;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentVisualizacao extends Fragment{

    private View view;
    private Trabalho trabalho;

    private ImageButton btAddDisciplina, btExcluirTrabalho, btEditarTrabalho, btCancelarV, btSalvarTrabalhoV;
    private EditText etNome, etAutor, etLinguagem, etGitHub, etUsuario, etSenha;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_visualizacao, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("trabalho");

        btAddDisciplina = (ImageButton) getActivity().findViewById(R.id.btAddDisciplina);
        btAddDisciplina.setVisibility(View.INVISIBLE);
        btAddDisciplina.setClickable(false);

        etNome          = (EditText)    view.findViewById(R.id.etNomeV);
        etAutor         = (EditText)    view.findViewById(R.id.etAutorV);
        etLinguagem     = (EditText)    view.findViewById(R.id.etLinguagemV);
        etGitHub        = (EditText)    view.findViewById(R.id.etGitHubV);
        etUsuario       = (EditText)    view.findViewById(R.id.etUsuarioV);
        etSenha         = (EditText)    view.findViewById(R.id.etSenhaV);

        trabalho = new Trabalho();
        Bundle bundle = getArguments();
        trabalho = (Trabalho) bundle.getSerializable("trabalho");

        etNome.setText(trabalho.getNome());
        etAutor.setText(trabalho.getAutor());
        etLinguagem.setText(trabalho.getLinguagem());
        etGitHub.setText(trabalho.getGithub());
        etUsuario.setText(trabalho.getUsuario());
        etSenha.setText(trabalho.getSenha());

        btCancelarV = (ImageButton) view.findViewById(R.id.btCancelarV);

        btCancelarV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btAddDisciplina.setVisibility(View.VISIBLE);
                btAddDisciplina.setClickable(true);

                fragmentManager = getActivity().getSupportFragmentManager();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.containerPrincipal, new FragmentInicial(), "FragmentInicial");
                fragmentTransaction.commit();
            }
        });

        btEditarTrabalho = (ImageButton) view.findViewById(R.id.btEditTrabalhoV);

        btEditarTrabalho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNome.isEnabled()) {
                    etNome.setEnabled(true);
                    etAutor.setEnabled(true);
                    etLinguagem.setEnabled(true);
                    etGitHub.setEnabled(true);
                    etUsuario.setEnabled(true);
                    etSenha.setEnabled(true);
                }else{
                    etNome.setEnabled(false);
                    etAutor.setEnabled(false);
                    etLinguagem.setEnabled(false);
                    etGitHub.setEnabled(false);
                    etUsuario.setEnabled(false);
                    etSenha.setEnabled(false);
                    etNome.setText(trabalho.getNome());
                    etAutor.setText(trabalho.getAutor());
                    etLinguagem.setText(trabalho.getLinguagem());
                    etGitHub.setText(trabalho.getGithub());
                    etUsuario.setText(trabalho.getUsuario());
                    etSenha.setText(trabalho.getSenha());
                }
            }
        });

        btSalvarTrabalhoV = (ImageButton) view.findViewById(R.id.btSalvarTrabalhoV);

        btSalvarTrabalhoV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    Toast.makeText(getActivity(), "Trabalho atualizado", Toast.LENGTH_SHORT).show();

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

        btExcluirTrabalho = (ImageButton) view.findViewById(R.id.btExcluirTrabalhoV);

        btExcluirTrabalho.setOnClickListener(new View.OnClickListener() {
            AlertDialog alerta;
            @Override
            public void onClick(View v) {AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Excluir");
                builder.setMessage("Deseja realmente excluir este Trabalho");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        databaseReference.child(trabalho.getId()).removeValue();
                        Toast.makeText(getActivity(), "Trabalho excluído", Toast.LENGTH_SHORT).show();
                        btAddDisciplina.setVisibility(View.VISIBLE);
                        btAddDisciplina.setClickable(true);

                        fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.containerPrincipal, new FragmentInicial(), "FragmentInicial");
                        fragmentTransaction.commit();

                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alerta = builder.create();
                alerta.show();
            }
        });

        return view;
    }
}
