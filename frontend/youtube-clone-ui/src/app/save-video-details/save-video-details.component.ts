import {Component, inject, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../video.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {finalize, Subscription, tap} from "rxjs";
import {HttpEvent, HttpEventType, HttpResponse} from "@angular/common/http";
import {VideoDto} from "../model/video-dto";

@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css']
})
export class SaveVideoDetailsComponent implements OnInit {

  @Input() requiredFileType: string = '';

  saveVideoDetailsForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  videoStatus: FormControl = new FormControl('');

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];
  selectedFile!: File;

  announcer = inject(LiveAnnouncer);
  selectedFileName: string = '';
  videoId: string = '';
  fileSelected: boolean = false;
  uploadProgress: number = 0;
  uploadSub: Subscription | undefined;
  thumbnailUrl: string = '';
  videoUrl!: string;
  videoAvailable!: boolean;
  likes!: number;
  dislikes!: number;
  userId!: string;
  viewCount!: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    private videoService: VideoService,
    private snackBar: MatSnackBar
  ) {
    this.saveVideoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description,
      videoStatus: this.videoStatus,
    });

    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    if (this.videoId) {
      this.videoService.getVideo(this.videoId).subscribe(data => {
        this.videoUrl = data.videoUrl;
        this.videoAvailable = true;
        this.thumbnailUrl = data.thumbnailUrl;
        this.saveVideoDetailsForm.setValue({
          title: data.title || '',
          description: data.description || '',
          videoStatus: data.videoStatus || ''
        });
        this.tags = data.tags || [];
      });
    }
  }

  ngOnInit(): void {}

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.tags.push(value);
    }
    event.chipInput!.clear();
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
      this.announcer.announce(`Removed ${tag}`);
    }
  }

  edit(tag: string, event: MatChipEditedEvent): void {
    const value = event.value.trim();
    if (!value) {
      this.remove(tag);
      return;
    }
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags[index] = value;
    }
  }

  onFileSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0) {
      this.selectedFile = inputElement.files[0];
      this.selectedFileName = this.selectedFile.name;
      this.thumbnailUrl = '';
      this.fileSelected = true;
    }
  }

  onUpload(): void {
    if (this.selectedFile) {
      const upload$ = this.videoService.uploadThumbnail(this.selectedFile, this.videoId).pipe(
        tap((event: HttpEvent<any>) => {
          if (event.type === HttpEventType.UploadProgress && event.total) {
            this.uploadProgress = Math.round(100 * (event.loaded / event.total));
          }
        }),
        finalize(() => this.reset())
      );

      this.uploadSub = upload$.subscribe((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          this.thumbnailUrl = event.body;
          this.snackBar.open("Thumbnail upload successful", "OK");
        }
      });
    }
  }

  cancelUpload(): void {
    if (this.uploadSub) {
      this.uploadSub.unsubscribe();
    }
    this.reset();
  }

  reset(): void {
    this.uploadProgress = 0;
    this.uploadSub = undefined;
    this.fileSelected = false;
    this.selectedFileName = '';
  }

  saveVideo(): void {
    const videoMetaData: VideoDto = {
      id: this.videoId,
      title: this.saveVideoDetailsForm.get('title')?.value,
      description: this.saveVideoDetailsForm.get('description')?.value,
      tags: this.tags,
      videoUrl: this.videoUrl,
      videoStatus: this.saveVideoDetailsForm.get('videoStatus')?.value,
      thumbnailUrl: this.thumbnailUrl,
      likes: this.likes,
      dislikes: this.dislikes,
      viewCount: this.viewCount,
      userId: this.userId
    };
    this.videoService.saveVideo(videoMetaData).subscribe(() => {
      this.snackBar.open("Video Metadata updated successfully", "OK");
    });
  }
}
