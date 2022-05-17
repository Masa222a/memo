package com.android.example.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.example.memo.databinding.FragmentStudyBinding
import io.realm.Realm
import io.realm.kotlin.where
import kotlin.random.Random


class StudyFragment : Fragment() {
    private var _binding: FragmentStudyBinding? = null
    private val binding get() = _binding!!

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudyBinding.inflate(inflater, container, false)
        realm.executeTransaction {
            val words = realm.where<Memo>().findAll()
            val randomNum = Random.nextInt(words.size)
            val word = realm.where<Memo>().equalTo("id", randomNum).findFirst()
            word?.name = binding.engWord.text.toString()
            word?.means = binding.jpWord.text.toString()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        realm.close()
    }
}