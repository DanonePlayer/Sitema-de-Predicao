import { TestBed } from '@angular/core/testing';

import { DadosbrutosService } from './dadosbrutos.service';

describe('DadosbrutosService', () => {
  let service: DadosbrutosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DadosbrutosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
