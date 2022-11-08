package dispo.train.fcba.cl.flotatrainmovil.views;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.adapters.DispoAdaptador;
import dispo.train.fcba.cl.flotatrainmovil.adapters.DispoNivelesAdaptador;
import dispo.train.fcba.cl.flotatrainmovil.helpers.dispoAPI;
import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasActivity extends AppCompatActivity {

    private List<Disponibilidad> datos;
    private RecyclerView rvDispoInfoBus;
    private String fecha;
    private String tipo;
    private List<Disponibilidad> datosAnterior;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        fecha=bundle.getString("fecha");
        tipo=bundle.getString("tipo");
        if(tipo.equals("TC")){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Dispo Tractocamiones");
        }else{
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Dispo Semirremolques");
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //Add back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvDispoInfoBus= (RecyclerView)findViewById(R.id.rvDispoInfoBus);

        datosAnterior= (List<Disponibilidad>) bundle.getSerializable("datos");
        //agregar si hizo una consulta a nivel 2

        setUpRecyclerView();
    }


        private void setUpRecyclerView(){
            rvDispoInfoBus.setLayoutManager(new LinearLayoutManager(this));
            rvDispoInfoBus.setHasFixedSize(true);
            //Adaptador aqui
            Log.d("FECHA",datosAnterior.get(0).getFECHA());
            DispoNivelesAdaptador adaptador = new DispoNivelesAdaptador(datosAnterior);
            rvDispoInfoBus.setAdapter(adaptador);
            //configuracion rv
            rvDispoInfoBus.setLayoutManager( new GridLayoutManager(this,1));
            rvDispoInfoBus.addItemDecoration( new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            rvDispoInfoBus.setItemAnimator(new DefaultItemAnimator());

        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_backArrow = 16908332;
        if(item.getItemId()== id_backArrow){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_categorias, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("datos", (Serializable) datosAnterior);
                    bundle.putString("fecha", fecha);
                    bundle.putString("tipo", tipo);
                    TabFragmentSer tab1 = new TabFragmentSer();
                    tab1.setArguments(bundle);
                    return tab1;
                case 1:
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("fecha", fecha);
                    bundle2.putString("tipo", tipo);
                    TabFragmentMar tab2 = new TabFragmentMar();
                    tab2.setArguments(bundle2);
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SERVICIOS";
                case 1:
                    String tituloTab2;
                    if (tipo.equals("TC")){
                        tituloTab2="MARCAS";
                    }else{
                        tituloTab2="TIPOS";
                    }
                    return tituloTab2;
            }
            return null;
        }
    }
}
