package com.android.example.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.example.memo.databinding.FragmentAddwordBinding
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class AddWordFragment : Fragment() {

    private var _binding: FragmentAddwordBinding? = null
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
        _binding = FragmentAddwordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener { saveWord(it) }
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_AddWordFragment_to_FirstFragment)
        }
    }

    private fun saveWord(view: View) {
        realm.executeTransaction { db: Realm ->
            val maxId = db.where<Memo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1L
            val memo = db.createObject<Memo>(nextId)
            memo.name = binding.createEnword.text.toString()
            memo.means = binding.createJpword.text.toString()
        }
        Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
            .setAction("戻る") { findNavController().popBackStack() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}