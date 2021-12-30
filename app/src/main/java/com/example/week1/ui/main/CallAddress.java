package com.example.week1.ui.main;
import java.util.ArrayList;

import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.content.Context;
import android.content.ContentResolver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.database.Cursor;
import android.provider.ContactsContract;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.widget.EditText;
import com.example.week1.R;

import com.example.week1.databinding.FragmentFirstBinding;
import androidx.recyclerview.widget.DividerItemDecoration;
public class CallAddress extends Fragment {
    private ArrayList<PhoneBook> addrList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager layoutManager;
    private Context context;
    private ArrayList<PhoneBook> list;
    private ArrayList<PhoneBook> arraylist;
    private EditText editSearch;
    private CustomAdapter mAdapter;
    private CustomAdapter adapter;
    private ContentResolver contentResolver;

    public static CallAddress newInstance() {
        CallAddress fragment = new CallAddress();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mAdapter = new CustomAdapter(context, addrList);
        contentResolver = getActivity().getContentResolver();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_first, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = root.findViewById(R.id.recyclerview_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        updateData();
//        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Thread(new Runnable(){
//                    @Override
//                    public void run(){
//                        updateData();
//                    }
//                }
//                ).start();
//                //updateData();
//                pullToRefresh.setRefreshing(false);
//            }
//        });
        editSearch = (EditText) root.findViewById(R.id.editSearch);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview_list);

        list = new ArrayList<PhoneBook>();
        settingList();
        arraylist = new ArrayList<PhoneBook>();
        arraylist.addAll(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(context,list);
        recyclerView.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할 때마다 호출됨
                // search 메소드 호출
                String text = editSearch.getText().toString();
                search(text);
            }
        });
        return root;
    }
    private void updateData(){
        int i = 1;
        addrList.clear();
        String [] arrProjection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        String[] arrPhoneProjection = {
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor clsCursor=contentResolver.query (
                ContactsContract.Contacts.CONTENT_URI,
                arrProjection,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1" ,
                null,
                "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC"
        );

        if (clsCursor.moveToFirst()) {
            do {
                /* Get phone number. This requires new cursor since phone number is
                not located in ContactContract.Contacts */
                String contactId = clsCursor.getString(0);

                // phone number
                Cursor phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        arrPhoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null
                );
                phoneCursor.moveToNext();
                String realnumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String phone = realnumber.replace("-","");
                phoneCursor.close();


                /* Make ContactInfo and update the array */
                PhoneBook contactInfo = new PhoneBook(Integer.toString(i),
                        clsCursor.getString(clsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                        phone
                );
                i++;
                addrList.add(contactInfo);
            } while (clsCursor.moveToNext());
        }
        clsCursor.close();
        /* Notify to the adapter */
        mAdapter.notifyDataSetChanged();
    }
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).getName().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
                else if (arraylist.get(i).getTel().toLowerCase().contains(charText))
                {
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){
        list = addrList;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
