package com.books.app.ui.details_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.books.app.databinding.FragmentDetailsBinding
import com.books.app.firebase.model.Book
import com.books.app.ui.main_fragment.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.books.app.ui.main_fragment.SubAdapter


@AndroidEntryPoint
class DetailsFragment : Fragment(), OnSnapPositionChangeListener {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: HorizontalCarouselRecyclerView
    private var sliderBooks: List<Book> = listOf()
    private var selectedBookId = 0
    private lateinit var smoothScroller: SmoothScroller
    private var likeBooksList = listOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.carousel
        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        val adapter = SliderAdapter(sliderBooks)
        setPropsForLayoutManager(linearLayoutManager)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.initialize(adapter)

        arguments?.let {
            selectedBookId = it.getInt("selected_book_id")
            viewModel.getBookById(selectedBookId) }

        smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }

        observeBooks()
        viewModel.setLikeBooks()

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.isNestedScrollingEnabled = false

        val snapPos = snapHelper.getSnapPosition(recyclerView)
        Log.i("snap pos", "onViewCreated: $snapPos")
        val snapOnScrollListener = SnapOnScrollListener(snapHelper,SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, this)
        recyclerView.addOnScrollListener(snapOnScrollListener)

        viewModel.selectedBook.observe(viewLifecycleOwner){
            binding.numOfReaders.text = it.views
            binding.numOfLikes.text = it.likes
            binding.numOfQuotes.text = it.quotes
            binding.genre.text = it.genre
            binding.bookSummary.text = it.summary
        }

        val likeRv = binding.youLlAlsoLikeRecycler
        val likeLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        likeRv.layoutManager = likeLayoutManager

        viewModel.youWillLikeBooksList.observe(viewLifecycleOwner){
            Log.i("like books", "onViewCreated: $it")
            likeBooksList = it
            likeRv.adapter = SubAdapter(likeBooksList,2)
        }
        
        likeRv.adapter = SubAdapter(likeBooksList,2)

    }

    private fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    private fun setPropsForLayoutManager(linearLayoutManager: LinearLayoutManager){
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
    }

    private fun observeBooks(){
        viewModel.booksData.observe(viewLifecycleOwner){
            sliderBooks = it
            val newAdapter = SliderAdapter(sliderBooks)
            recyclerView.adapter = newAdapter
            smoothScroller.targetPosition = selectedBookId
            (recyclerView.layoutManager as ZoomRecyclerLayout).startSmoothScroll(smoothScroller)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSnapPositionChange(position: Int) {
        Log.i("snapper ebat'", "onSnapPositionChange: $position")
        viewModel.getBookById(position)
        viewModel.setLikeBooks()
        Log.i("like books", "onViewCreated: ${viewModel.youWillLikeBooksList.value}")
    }
}