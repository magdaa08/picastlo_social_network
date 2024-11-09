package com.picastlo.pipelineservice.data

import com.picastlo.pipelineservice.presentation.model.Pipeline
import com.picastlo.pipelineservice.presentation.repository.PipelineRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DataSeeder(private val pipelineRepository: PipelineRepository) : CommandLineRunner {

    private val pipelineNames = listOf(
        "Mountain View", "Beach Sunset", "City Skyline", "Family Portrait", "Urban Street Photography",
        "Wildlife Safari", "Autumn Forest", "Night Sky", "Street Art", "Vintage Car",
        "Coastal Landscape", "Flower Field", "Classic Architecture", "Rainy Day in the City",
        "Abstract Shapes", "Interior Design", "Snowy Landscape", "Food Photography",
        "Sports Action", "Pet Portrait", "Luxury Watches"
    )

    private val pipelineDescriptions = listOf(
        "Captured in the heart of the mountains, perfect for showcasing nature's serenity.",
        "A stunning sunset over the beach, with vibrant colors and warm hues.",
        "The city's skyline at dusk, featuring illuminated buildings and street lights.",
        "A family portrait with soft lighting and a warm, cozy atmosphere.",
        "An urban street scene capturing the hustle and bustle of city life.",
        "A wildlife safari moment with animals in their natural habitat.",
        "A beautiful autumn forest scene with golden and red leaves.",
        "A breathtaking view of the night sky, featuring stars and distant constellations.",
        "A piece of street art showcasing urban creativity and culture.",
        "A vintage car in pristine condition, captured in high resolution.",
        "A tranquil coastal landscape with clear waters and calm skies.",
        "A colorful field of blooming flowers in the peak of spring.",
        "Classic architecture with intricate details and a timeless design.",
        "A rainy day in the city with reflections on wet pavement and misty surroundings.",
        "Abstract shapes and forms with an artistic and colorful design.",
        "Modern interior design showcasing minimalist style and soft colors.",
        "A picturesque snowy landscape featuring snow-covered trees and hills.",
        "Close-up food photography highlighting vibrant ingredients and textures.",
        "High-action sports moment with intense motion and dramatic angles.",
        "A playful portrait of a beloved pet, captured in its natural state.",
        "High-end luxury watches with precision detailing and a stylish design."
    )

    override fun run(vararg args: String?) {
        seedPipelines()
    }


    @Transactional
    fun seedPipelines() {

        if (pipelineRepository.count() == 0L) {
            val pipelines = (1..20).map { index ->
                Pipeline(
                    name = pipelineNames[index - 1],
                    description = pipelineDescriptions[index - 1],
                    ownerId = getRandomOwnerId(),
                    transformations = generateTransformationLogic(),
                    initialImage = ByteArray(0) // Placeholder for an empty image
                )
            }
            pipelineRepository.saveAll(pipelines)
        }
    }

    // Helper function to generate pipeline transformations
    private fun generateTransformationLogic(): String {
        val transformations = listOf(
            "Resize 800x600",
            "Apply Grayscale",
            "Upscale to 4K",
            "Convert to PNG",
            "Apply Sepia",
            "Rotate 90 degrees",
            "Add Watermark 'Picastlo'",
            "Crop 400x300",
            "Resize to 500x500 and Crop",
            "Enhance Resolution"
        )
        return transformations.random()
    }

    // Helper function to generate owner of a pipeline
    private fun getRandomOwnerId(): Long {
        return (1..20).random().toLong()
    }
}
