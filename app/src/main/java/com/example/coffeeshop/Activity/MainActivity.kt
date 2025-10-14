package com.example.coffeeshop.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshop.Adapter.CategoryAdapter
import com.example.coffeeshop.Adapter.PopularAdapter
import com.example.coffeeshop.Adapter.SpecialAdapter
import com.example.coffeeshop.ViewModel.MainViewModel
import com.example.coffeeshop.databinding.ActivityMainBinding

/**
 * MainActivity - Entry point of the Coffee Shop app.
 * Displays categories, popular items, and special offers using RecyclerViews.
 * Uses a ViewModel (MainViewModel) to load data asynchronously and observe changes.
 */
class MainActivity : AppCompatActivity() {

    // ViewBinding allows direct access to XML views without findViewById()
    private lateinit var binding: ActivityMainBinding

    // ViewModel to manage UI-related data lifecycle
    private val viewModel = MainViewModel()

    // Adapters for each RecyclerView
    private val categoryAdapter = CategoryAdapter(mutableListOf())
    private val popularAdapter = PopularAdapter(mutableListOf())
    private val specialAdapter = SpecialAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI components
        setupRecyclerViews()
        observeData()
        initBottomMenu()
    }

    /**
     * Initializes bottom navigation menu buttons.
     * Navigates to the CartActivity when the cart button is clicked.
     */
    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            // Start CartActivity when cart button is clicked
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    /**
     * Configures all RecyclerViews in the layout with layout managers and adapters.
     * - Category: horizontal scroll
     * - Popular: horizontal scroll
     * - Special: vertical scroll
     */
    private fun setupRecyclerViews() {
        // Category list (horizontal)
        binding.recyclerViewCategory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
            isNestedScrollingEnabled = false // Prevents scroll conflicts
        }

        // Popular items list (horizontal)
        binding.recyclerViewPopular.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
            isNestedScrollingEnabled = false
        }

        // Special items list (vertical)
        binding.recyclerViewSpecial.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = specialAdapter
            isNestedScrollingEnabled = false
        }
    }

    /**
     * Observes LiveData from the ViewModel and updates RecyclerViews
     * when new data (categories, popular items, specials) is available.
     * Also controls progress bar visibility during loading.
     */
    private fun observeData() {
        // ----------------- CATEGORY DATA -----------------
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.loadCategory().observe(this) { categories ->
            binding.progressBarCategory.visibility = View.GONE
            categoryAdapter.items.clear()
            categoryAdapter.items.addAll(categories)
            categoryAdapter.notifyDataSetChanged()
        }

        // ----------------- POPULAR DATA -----------------
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.loadPopular().observe(this) { popularItems ->
            binding.progressBarPopular.visibility = View.GONE
            popularAdapter.items.clear()
            popularAdapter.items.addAll(popularItems)
            popularAdapter.notifyDataSetChanged()
        }

        // ----------------- SPECIAL DATA -----------------
        binding.progressBarSpecial.visibility = View.VISIBLE
        viewModel.loadSpecial().observe(this) { specialItems ->
            binding.progressBarSpecial.visibility = View.GONE
            specialAdapter.items.clear()
            specialAdapter.items.addAll(specialItems)
            specialAdapter.notifyDataSetChanged()
        }
    }
}
