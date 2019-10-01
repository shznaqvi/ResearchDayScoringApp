package edu.aku.hassannaqvi.researchdayscoring.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.animation.Animations;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ListItemBinding;

public class AllProjectListAdapter extends RecyclerView.Adapter<AllProjectListAdapter.ViewHolder> {

    Context context;
    List<ProjectsContract> list;

    OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public AllProjectListAdapter(Context context, List<ProjectsContract> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        ListItemBinding bi = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item, viewGroup, false);

        return new ViewHolder(bi);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        if (list.get(i) != null) {

            holder.bi.projectTitle.setText(list.get(i).getTitle());
            holder.bi.authorFirstLetter.setText(String.valueOf(list.get(i).getAuthor().charAt(0)));

            holder.bi.viewMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean show = toggleLayout(!list.get(i).isExpanded(), v, holder.bi.layoutExpand);
                    list.get(i).setExpanded(show);
                    holder.bi.authorName.setText(list.get(i).getAuthor());
                    holder.bi.projectAbstract.setText(list.get(i).getAbstract());
                    holder.bi.projectTheme.setText(list.get(i).getTheme());

                }
            });

            holder.bi.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickListener.OnItemClick(list.get(i));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding bi;

        public ViewHolder(@NonNull ListItemBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(ProjectsContract contract);
    }

}
