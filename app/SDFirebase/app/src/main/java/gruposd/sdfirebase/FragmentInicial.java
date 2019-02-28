package gruposd.sdfirebase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FragmentInicial extends Fragment{

    private View     view;
    private ListView lvTrabalho;
    private DatabaseReference databaseReference;

    private List<Trabalho> listaTrabalho;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inicial, container, false);

        lvTrabalho = (ListView) view.findViewById(R.id.lvTrabalho);

        databaseReference = FirebaseDatabase.getInstance().getReference("trabalho");

        listaTrabalho = new ArrayList<Trabalho>();

        final ArrayAdapter<Trabalho> arrayAdapter = new ArrayAdapter<Trabalho>(getActivity(), android.R.layout.simple_list_item_1, listaTrabalho);
        lvTrabalho.setAdapter(arrayAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listaTrabalho.add(dataSnapshot.getValue(Trabalho.class));
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        fragmentManager = getActivity().getSupportFragmentManager();

        lvTrabalho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("trabalho", listaTrabalho.get(position));

                Fragment fragment = new FragmentVisualizacao();
                fragment.setArguments(bundle);

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.containerPrincipal, fragment, "FragmentVisualizacao");
                fragmentTransaction.commit();

            }
        });

        return view;
    }
}
