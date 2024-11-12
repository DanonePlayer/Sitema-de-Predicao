import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelacriatokenComponent } from './telacriatoken.component';

describe('TelacriatokenComponent', () => {
  let component: TelacriatokenComponent;
  let fixture: ComponentFixture<TelacriatokenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelacriatokenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelacriatokenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
