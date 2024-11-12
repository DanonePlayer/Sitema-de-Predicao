import { TestBed } from '@angular/core/testing';

import { NtiApiService } from './nti-api.service';

describe('NtiApiService', () => {
  let service: NtiApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NtiApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
