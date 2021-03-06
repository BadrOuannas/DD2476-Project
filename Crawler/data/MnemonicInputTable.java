13
https://raw.githubusercontent.com/CoboVault/cobo-vault-cold/master/app/src/main/java/com/cobo/cold/mnemonic/MnemonicInputTable.java
/*
 * Copyright (c) 2020 Cobo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * in the file COPYING.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cobo.cold.mnemonic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cobo.cold.R;
import com.cobo.cold.databinding.MnemonicInputItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MnemonicInputTable extends RecyclerView {

    public static final int TWELVE = 12;
    public static final int EIGHTEEN = 18;
    public static final int TWEENTYFOUR = 24;
    private final List<ObservableField<String>> wordsList = new ArrayList<>();
    private int mMnemonicCount = TWEENTYFOUR;
    private MnemonicAdapter mAdapter;

    private boolean editable = true;

    public MnemonicInputTable(@NonNull Context context) {
        this(context, null);
    }

    public MnemonicInputTable(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MnemonicInputTable(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTable();
        fillWordlist();
    }

    private void fillWordlist() {
        for (int i = 0; i < mMnemonicCount; i++) {
            wordsList.add(new ObservableField<>());
        }
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    private void initTable() {
        setLayoutManager(new GridLayoutManager(getContext(), 3));
        addItemDecoration(new TableItemDecoration(getContext()));
        mAdapter = new MnemonicAdapter();
        setAdapter(mAdapter);
    }

    public List<ObservableField<String>> getWordsList() {
        return wordsList;
    }

    public void setMnemonicNumber(@MnemonicCount int mnemonicType) {
        mMnemonicCount = mnemonicType;
        wordsList.clear();
        fillWordlist();
        mAdapter.setMnemonicCount(mMnemonicCount);
        mAdapter.notifyDataSetChanged();
    }

    @IntDef({TWELVE, EIGHTEEN, TWEENTYFOUR})
    public @interface MnemonicCount {
    }

    class MnemonicAdapter extends RecyclerView.Adapter<MnemonicHolder> {

        private int count;

        public void setMnemonicCount(@MnemonicCount int count) {
            this.count = count;
        }

        @NonNull
        @Override
        public MnemonicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            MnemonicInputItemBinding binding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.mnemonic_input_item, parent, false);

            return new MnemonicHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MnemonicHolder holder, int position) {
            holder.binding.setIndex(position);
            holder.binding.setWord(wordsList.get(position));

            if (!editable) {
                holder.binding.input.setFocusable(false);
                holder.binding.input.setFocusableInTouchMode(false);
            }
            if (position < mMnemonicCount - 1) {
                holder.binding.input.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            } else {
                holder.binding.input.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }

    private class MnemonicHolder extends ViewHolder {
        private final MnemonicInputItemBinding binding;

        public MnemonicHolder(@NonNull MnemonicInputItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
