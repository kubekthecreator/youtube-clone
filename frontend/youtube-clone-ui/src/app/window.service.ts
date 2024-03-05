import { Injectable, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';

@Injectable()
export class WindowService {
  constructor(@Inject(DOCUMENT) private _doc: Document) {}

  getWindow(): Window | null {
    return this._doc.defaultView;
  }

  getLocation(): Location {
    return this._doc.location;
  }

  createElement(tag: string): HTMLElement {
    return this._doc.createElement(tag);
  }
}
