package com.anna.todolist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.anna.todolist.R;
import com.anna.todolist.model.TodoTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class TodoTaskAdapter extends RecyclerView.Adapter<TodoTaskAdapter.TodoViewHolder> {
    private List<TodoTask> mTasks;
    private OnClickListener mClickListener;

    public TodoTaskAdapter(List<TodoTask> tasks, OnClickListener clickListener) {
        mTasks = tasks;
        mClickListener = clickListener;
    }

    public void setTasks(List<TodoTask> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    //Jak ma wyglądać widok(praktycznie zawsze wygląda tak samo - poza nazwą xml i nazwą obiektu)
    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.list_item_todo, parent, false);
        return new TodoViewHolder(rowView);
    }


    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        // Pobranie elementu danych na pozycji position
        TodoTask task = mTasks.get(position);
        // Uzupełnienie widoku(holder) na podstawie pobranego obiektu
        holder.mBlockListeners = true; // Blokujemy wysyłanie powiadomień o zmianie checkboxa i kliknięciu
        holder.mCurrentPosition = task.getId();
        holder.mCurrentTask = task;
        holder.mTitle.setText(task.getName());
        holder.mDone.setChecked(task.isDone());
        holder.mBlockListeners = false; // Odblokowujemy powiadomienia, żeby poprawnie reagować na zdarzenia użytkowników

    }

    //Ile elementów ma zawierać
    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.task_done)
        CheckBox mDone;
        @BindView((R.id.task_title))
        TextView mTitle;

        TodoTask mCurrentTask;
        int mCurrentPosition;
        // True - ponieważ na początku kiedy powstaje wiersz i jest przed przypisaniem pierwszego zadania
        // nie chcemy żeby uruchamiały się jakiekolwiek funkcje dotyczące zdarzeń (np. 0nClick, onChecked)
        boolean mBlockListeners = true;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onItemClick() {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onClick(mCurrentTask, mCurrentPosition);
            }
        }

        @OnCheckedChanged(R.id.task_done)
        void onCheckedChange(boolean checked) {
            if (mClickListener != null && !mBlockListeners) {
                mClickListener.onTaskDoneChanged(mCurrentTask, mCurrentPosition, checked);
            }
        }
    }

    public interface OnClickListener {
        void onClick(TodoTask task, int position);

        void onTaskDoneChanged(TodoTask task, int position, boolean isDone);
    }
}