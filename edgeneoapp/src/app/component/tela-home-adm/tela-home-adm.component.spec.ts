import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaHomeAdmComponent } from './tela-home-adm.component';

describe('TelaHomeAdmComponent', () => {
  let component: TelaHomeAdmComponent;
  let fixture: ComponentFixture<TelaHomeAdmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaHomeAdmComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaHomeAdmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
