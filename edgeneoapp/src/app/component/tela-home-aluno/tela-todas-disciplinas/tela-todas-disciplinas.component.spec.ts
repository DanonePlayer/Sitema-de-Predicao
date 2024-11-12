import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaTodasDisciplinasComponent } from './tela-todas-disciplinas.component';

describe('TelaTodasDisciplinasComponent', () => {
  let component: TelaTodasDisciplinasComponent;
  let fixture: ComponentFixture<TelaTodasDisciplinasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelaTodasDisciplinasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TelaTodasDisciplinasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
