package com.books.app.ui.main_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.books.app.databinding.MainFragmentBinding
import com.books.app.firebase.model.Book
import com.books.app.firebase.model.BookWithGenre
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerView:RecyclerView
    private val listBooksGenre: MutableList<BookWithGenre> = mutableListOf()
    private val mapGenreBooks = hashMapOf<String,MutableList<Book>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listBooksGenre.clear()

        viewModel.sliderData.observe(viewLifecycleOwner){
            val imageList = mutableListOf<SlideModel>()
            it.forEach { imageList.add(SlideModel(it.cover)) }

            binding.mainScreenSlider.setImageList(imageList, ScaleTypes.FIT)
        }

        binding.mainScreenSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                Log.i("slider", "onItemSelected: position:$position")
            }
        })

        viewModel.booksData.observe(viewLifecycleOwner){

            for (book in it){
                val genre = book.genre
                var list = mapGenreBooks[genre]

                if (list.isNullOrEmpty()){
                    list = mutableListOf()
                }
                list.add(book)
                mapGenreBooks[genre] = list
            }

            mapGenreBooks.forEach { (key, value) ->
                listBooksGenre.add(BookWithGenre(key,value))
            }

            recyclerView.adapter = MainAdapter(requireContext(),listBooksGenre)

        }
        viewModel.setLikeBooks()
        recyclerView = binding.mainScreenRecycler
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MainAdapter(requireContext(),listBooksGenre)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}