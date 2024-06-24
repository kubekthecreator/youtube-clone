import {Component, OnInit} from '@angular/core';
import {VideoService} from "../video.service";
import {VideoDto} from "../model/video-dto";

@Component({
  selector: 'app-featured',
  templateUrl: './featured.component.html',
  styleUrl: './featured.component.css'
})
export class FeaturedComponent implements OnInit {
  featuredVideos: Array<VideoDto> = [];
  isLoading = true;
  errorMessage = '';

  constructor(private videoService: VideoService) {}

  ngOnInit(): void {
    this.loadFeaturedVideos();
  }

  private loadFeaturedVideos(): void {
    this.videoService.getAllVideos().subscribe({
      next: (response) => {
        this.featuredVideos = response;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load featured videos. Please try again later.';
        this.isLoading = false;
        console.error('Error loading featured videos:', error);
      }
    });
  }
}
