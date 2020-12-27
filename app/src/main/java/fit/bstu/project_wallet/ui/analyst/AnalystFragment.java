package fit.bstu.project_wallet.ui.analyst;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import fit.bstu.project_wallet.R;

public class AnalystFragment extends Fragment {

    private AnalystViewModel analystViewModel;
    PieChart pieChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analystViewModel = new ViewModelProvider(this).get(AnalystViewModel.class);
        View root = inflater.inflate(R.layout.fragment_analyst, container, false);
        pieChart = root.findViewById(R.id.chart1);
        analystViewModel.getAnalyst();
        setupPieChart();
        return root;
    }
    public static final int[] MATERIAL_COLORS = {
            Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140), Color.rgb(118, 174, 175), Color.rgb(42, 109, 130),Color.rgb(64, 89, 128), Color.rgb(149, 165, 124),Color.rgb(222, 222, 222)
    };
    private void setupPieChart(){
        List <String> activity = analystViewModel.getCategoryName();
        List<Float> time = analystViewModel.getTransactValue();
        List<PieEntry> pieEntires = new ArrayList<>();
        for( int i = 0 ; i<time.size();i++){
            pieEntires.add(new PieEntry(time.get(i), activity.get(i)));
        }
        PieDataSet dataSet = new PieDataSet(pieEntires,"");
        dataSet.setColors(MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        //Chart attributes;
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setCenterText("Spending`s");
        pieChart.setCenterTextSize(20);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawMarkers(true);
        pieChart.setHoleRadius(55);
        pieChart.getDescription().setEnabled(false);

        //legend attributes
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(13);
        legend.setFormSize(11);
        legend.setFormToTextSpace(2);
    }
}