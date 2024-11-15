import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmacaoEmailComponent } from './confirmacao-email.component';

describe('ConfirmacaoEmailComponent', () => {
  let component: ConfirmacaoEmailComponent;
  let fixture: ComponentFixture<ConfirmacaoEmailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmacaoEmailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfirmacaoEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
