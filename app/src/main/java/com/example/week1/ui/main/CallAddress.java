package com.example.week1.ui.main;
import java.util.ArrayList;

import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.content.Context;
import android.content.ContentResolver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.week1.R;
import com.example.week1.databinding.FragmentFirstBinding;

public class CallAddress extends Fragment {
    private ArrayList<PhoneBook> addrList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FragmentFirstBinding binding;
    private Context context;
    private CustomAdapter mAdapter;
    private PageViewModel pageViewModel;
    public static CallAddress newInstance() {
        CallAddress fragment = new CallAddress();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addrList = new ArrayList<>();
        mAdapter = new CustomAdapter(addrList);
        ContentResolver resolver = getActivity().getContentResolver();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = root.findViewById(R.id.recyclerview_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //final TextView textView = binding.sectionLabel;
//        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
//    // 2. 전화번호가 저장되어 있는 테이블 주소값(Uri)을 가져오기
//    Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//
//    // 3. 테이블에 정의된 칼럼 가져오기
//    // ContactsContract.CommonDataKinds.Phone 이 경로에 상수로 칼럼이 정의
//    String[] projection = { ContactsContract.CommonDataKinds.Phone.CONTACT_ID // 인덱스 값, 중복될 수 있음 -- 한 사람 번호가 여러개인 경우
//            ,  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
//            ,  ContactsContract.CommonDataKinds.Phone.NUMBER};
//
//    // 4. ContentResolver로 쿼리를 날림 -> resolver 가 provider 에게 쿼리하겠다고 요청
//    Cursor cursor = resolver.query(phoneUri, projection, null, null, null);
//
//    // 4. 커서로 리턴된다. 반복문을 돌면서 cursor 에 담긴 데이터를 하나씩 추출
//        if(cursor != null){
//        while(cursor.moveToNext()){
//            // 4.1 이름으로 인덱스를 찾아준다
//            int idIndex = cursor.getColumnIndex(projection[0]); // 이름을 넣어주면 그 칼럼을 가져와준다.
//            int nameIndex = cursor.getColumnIndex(projection[1]);
//            int numberIndex = cursor.getColumnIndex(projection[2]);
//            // 4.2 해당 index 를 사용해서 실제 값을 가져온다.
//            String id = cursor.getString(idIndex);
//            String name = cursor.getString(nameIndex);
//            String number = cursor.getString(numberIndex);
//
//            PhoneBook phoneBook = new PhoneBook();
//            phoneBook.setId(id);
//            phoneBook.setName(name);
//            phoneBook.setTel(number);
//
//            datas.add(phoneBook);
//        }
//    }
//    // 데이터 계열은 반드시 닫아줘야 한다.
//        cursor.close();
//        return datas;


}
