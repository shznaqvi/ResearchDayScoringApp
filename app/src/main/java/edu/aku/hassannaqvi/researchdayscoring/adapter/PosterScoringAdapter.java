package edu.aku.hassannaqvi.researchdayscoring.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ItemCommentBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ItemContentBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ItemSectionBinding;
import edu.aku.hassannaqvi.researchdayscoring.model.Poster;

public class PosterScoringAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_ITEM = 1;
    private final int VIEW_SECTION = 0;
    private final int VIEW_COMMENT = 2;
    Context context;
    ArrayList<Poster> list;
    OnClickMinusPoint onClickMinusPoint;
    OnClickPlusPoint onClickPlusPoint;
    int points = 5;
    int initPoint = 1;

    public void setOnClickMinusPoint(OnClickMinusPoint onClickMinusPoint) {
        this.onClickMinusPoint = onClickMinusPoint;
    }

    public void setOnClickPlusPoint(OnClickPlusPoint onClickPlusPoint) {
        this.onClickPlusPoint = onClickPlusPoint;
    }

    public PosterScoringAdapter(Context context, ArrayList<Poster> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;

        if (list.get(i) != null) {
            if (i == VIEW_SECTION) {
                ItemSectionBinding bi = DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.item_section, viewGroup, false);
                holder = new SectionViewHolder(bi);
            }
            if (i == VIEW_ITEM) {
                ItemContentBinding bi = DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.item_content, viewGroup, false);
                holder = new ContentViewHolder(bi);
            }
            if (i == VIEW_COMMENT) {
                ItemCommentBinding bi = DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.item_comment, viewGroup, false);
                holder = new CommentViewHolder(bi);
            }
        }


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof SectionViewHolder) {

            SectionViewHolder vh = (SectionViewHolder) holder;
            vh.bi.titleSection.setText(list.get(i).sectionTitle);
        }
        if (holder instanceof ContentViewHolder) {

            final ContentViewHolder vh = (ContentViewHolder) holder;
            vh.bi.contentText.setText(list.get(i).sectionTitle);
            final int pos = i;
            vh.bi.pointsPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(pos).score <= 4) {
                        vh.bi.counterText.setText(String.valueOf(++list.get(pos).score));


                    }


                }
            });
            vh.bi.pointsMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(pos).score > 0) {
                        vh.bi.counterText.setText(String.valueOf(--list.get(pos).score));

                    }

                }
            });

        }

        if (holder instanceof CommentViewHolder) {

            CommentViewHolder vh = (CommentViewHolder) holder;
            vh.bi.comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    list.get(i).comment = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ContentViewHolder extends RecyclerView.ViewHolder {

        ItemContentBinding bi;

        public ContentViewHolder(@NonNull ItemContentBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {

        ItemSectionBinding bi;

        public SectionViewHolder(@NonNull ItemSectionBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ItemCommentBinding bi;

        public CommentViewHolder(@NonNull ItemCommentBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isSection) {
            return VIEW_SECTION;
        } else if (!list.get(position).isSection && !list.get(position).isComment) {
            return VIEW_ITEM;
        } else if (!list.get(position).isSection && list.get(position).isComment) {
            return VIEW_COMMENT;
        } else {
            return 4;
        }

    }

    public ArrayList<Poster> getList() {
        return list;
    }

    interface OnClickPlusPoint {

        void onClick(int i);
    }

    interface OnClickMinusPoint {

        void onClick(int i);
    }
}
